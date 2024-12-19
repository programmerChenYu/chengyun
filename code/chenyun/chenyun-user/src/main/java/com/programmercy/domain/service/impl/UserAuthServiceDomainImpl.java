package com.programmercy.domain.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.programmercy.constant.RedisPrefixConstant;
import com.programmercy.domain.service.UserAuthServiceDomain;
import com.programmercy.enums.ReturnParamsEnum;
import com.programmercy.infra.po.Location;
import com.programmercy.infra.po.Permissions;
import com.programmercy.infra.po.User;
import com.programmercy.infra.po.UserPunishments;
import com.programmercy.infra.service.*;
import com.programmercy.util.RedisUtil;
import com.programmercy.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: 用户认证服务实现类
 * Created by 爱吃小鱼的橙子 on 2024-12-16 11:47
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Slf4j
@Service
public class UserAuthServiceDomainImpl implements UserAuthServiceDomain {

    @Resource
    private UserService userService;
    @Resource
    private LocationService locationService;
    @Resource
    private RolesService rolesService;
    @Resource
    private PermissionsService permissionsService;
    @Resource
    private UserPunishmentsService userPunishmentsService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private ExecutorService userExecutorService;

    @Override
    public Boolean logout() {
        try {
            StpUtil.logout();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserVO getUserInfoByToken(String token) {
        String userId = StpUtil.getSession().get("USER_ID").toString();
        User user = userService.queryById(Long.valueOf(userId));
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserAuthServiceDomainImpl:getUserInfoByToken:user: [{}]", JSON.toJSONString(user));
        }
        return mapPO2VO(user);
    }

    @Override
    public String login(String username, String password, Integer clientType) throws ExecutionException, InterruptedException {
        String token = null;
        // 根据用户名和密码查询用户
        User user = userService.queryByUsernameAndPassword(username, password);
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserAuthServiceDomainImpl:login:user: [{}]", JSON.toJSONString(user));
        }
        // 用户可能不存在
        if (user != null) {
            // 用户存在
            // 1.获取用户角色
            Integer role = user.getRole();
            if (clientType.equals(0) && !role.equals(3)) {
                // 2 代表不是管理员不能登录管理系统
                token = ReturnParamsEnum.AUTH_NOT_ADMIN.getCode();
            } else {
                // 获取用户状态
                Integer userStatus = user.getUserStatus();
                if (userStatus == 0) {
                    // 0 代表已被封号
                    token = ReturnParamsEnum.AUTH_USER_EAL.getCode();
                } else {
                    // 进行登录
                    StpUtil.login(user.getUserId());
                    // 获取该用户的 token
                    token = StpUtil.getTokenValue();
                    // 将用户的 id 存储在 session 中
                    StpUtil.getSession().set("USER_ID", user.getUserId());
                    // 同时将用户的权限信息放入 redis 进行缓存，方便鉴权
                    // 2.根据角色获取具有的权限(放入新的线程中)
                    CompletableFuture<List<String>> allPermissionsStrFuture = CompletableFuture.supplyAsync(() -> {
                        List<String> permissionsStr = new ArrayList<>();
                        // 2.1 根据角色获取权限 id
                        List<Integer> permissionsIds = rolesService.queryPermissions(role);
                        // 2.2 根据权限 id，查找对应权限
                        List<Permissions> permissions = permissionsService.queryByIds(permissionsIds);

                        for (Permissions permission : permissions) {
                            permissionsStr.add(permission.getCode());
                        }
                        return permissionsStr;
                    }, userExecutorService);
                    CompletableFuture<List<String>> revokedPermissionsFuture = CompletableFuture.supplyAsync(() -> {
                        // 3.查看用户是否违规，如果违规则去除相应的权限(放入新的线程中)
                        List<String> revokedPermissions = new ArrayList<>();
                        if (userStatus == 1) {
                            UserPunishments userPunishments = userPunishmentsService.queryByUserId(user.getUserId());
                            revokedPermissions = Arrays.stream(userPunishments.getRevokedPermissions().split(";")).toList();
                        }
                        return revokedPermissions;
                    }, userExecutorService);
                    List<String> permissions = allPermissionsStrFuture.thenCombineAsync(revokedPermissionsFuture, (permissionsStr, revokedPermissions) -> {
                        permissionsStr.removeAll(revokedPermissions);
                        return permissionsStr;
                    }).get();
                    if (log.isInfoEnabled()) {
                        log.info("chenyun-user:domain:service:impl:UserAuthServiceDomainImpl:login:permissions: [{}]", JSON.toJSONString(permissions));
                    }
                    // 4.将权限存入 redis，可能失败，设置重试机制三次
                    AtomicInteger count = new AtomicInteger(0);
                    while (!redisUtil.setKVTtl(RedisPrefixConstant.USER_PERMISSION + user.getUserId(), JSON.toJSONString(permissions), StpUtil.getTokenTimeout(), TimeUnit.SECONDS)) {
                        count.incrementAndGet();
                        if (count.equals(3)) {
                            // 5 代表服务器问题，请重试
                            token = ReturnParamsEnum.SERVER_ERROR.getCode();
                            try {
                                // 设置间隔 500 毫秒重试一次
                                Thread.sleep(500L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            // 用户不存在
            token = ReturnParamsEnum.AUTH_USER_NOT_EXIST.getCode();
        }
        return token;
    }

    public UserVO mapPO2VO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setKey(user.getUserId().toString());
        Location location = locationService.queryById(user.getLocationId());
        StringBuilder sb = new StringBuilder();
        sb.append(location.getCountry()).append("/").append(location.getRegion()).append("/").append(location.getCity());
        userVO.setLocation(sb.toString());
        return userVO;
    }
}
