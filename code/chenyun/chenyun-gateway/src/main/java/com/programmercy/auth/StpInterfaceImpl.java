package com.programmercy.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.programmercy.constant.RedisPrefixConstant;
import com.programmercy.dto.UserDTO;
import com.programmercy.util.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-18 11:22
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 返回一个账号所拥有的权限码集合
     * @param loginId 账号id，即你在调用 StpUtil.login(id) 时写入的标识值。
     * @param loginType 账号体系标识。
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userId = StpUtil.getSession().get("USER_ID").toString();
        String permissions = redisUtil.getKV(RedisPrefixConstant.USER_PERMISSION + userId);
        List object = JSONObject.parseObject(permissions, List.class);
        List<String> list = new ArrayList<>();
        for (Object o : object) {
            list.add(o.toString());
        }
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
