package com.programmercy.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.programmercy.converter.UserConverter;
import com.programmercy.domain.service.UserProfileServiceDomain;
import com.programmercy.infra.po.User;
import com.programmercy.infra.service.LocationService;
import com.programmercy.infra.service.UserService;
import com.programmercy.vo.PageInfoVO;
import com.programmercy.vo.PagedUserVO;
import com.programmercy.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Description: 用户信息相关的服务的实现类
 * Created by 爱吃小鱼的橙子 on 2024-11-25 16:52
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
@Slf4j
public class UserProfileServiceDomainImpl implements UserProfileServiceDomain {

    @Resource
    private UserService userService;
    @Resource
    private LocationService locationService;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private UserConverter userConverter;
    @Resource
    private ExecutorService userThreadPool;

    @Override
    public List<PagedUserVO> pageForAListOfUsers(PagedUserVO pagedUserVO) {
        List<User> usersByPageInfo = userService.queryUsersByPageInfo(pagedUserVO.getCurrentPage(), pagedUserVO.getPageSize());
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:pageForAListOfUsers:usersByPageInfo: [{}]", JSON.toJSONString(usersByPageInfo));
        }
        return userConverter.mapPOList2PagedUserVOList(usersByPageInfo, pagedUserVO.getCurrentPage(), null);
    }

    @Override
    public Long numberOfUsers() {
        return userService.numberOfUsers();
    }

    @Override
    public Boolean userManagementOptionBatch(List<Long> userIds, Integer optionType) {
        Boolean flag = false;
        List<Integer> usersStatus = userService.queryUsersStatus(userIds);
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:userManagementOptionBatch:usersStatus: [{}]", JSON.toJSONString(usersStatus));
        }
        switch (optionType) {
            case 0:
                // 判断传过来的数据是否可以进行对应的批量操作
                for (Integer userStatus : usersStatus) {
                    if (userStatus == 0) {
                        return false;
                    }
                }
                // 进行封号处理
                flag = userService.sealedAccountBatch(userIds);
                break;
            case 1:
                for (Integer userStatus : usersStatus) {
                    if (userStatus != 0) {
                        return false;
                    }
                }
                // 进行接触封号处理
                flag = userService.unblockTheAccountBatch(userIds);
                break;
            case 2:
                for (Integer userStatus : usersStatus) {
                    if (userStatus != 2) {
                        return false;
                    }
                }
                // 进行违规处理
                flag = userService.illegalAccountBatch(userIds);
                break;
            case 3:
                for (Integer userStatus : usersStatus) {
                    if (userStatus != 1) {
                        return false;
                    }
                }
                // 进行接触违规处理
                flag = userService.cancelTheIllegalAccountBatch(userIds);
                break;
        }
        return flag;
    }

    @Override
    public Boolean userManagementOption(Long userId, Integer optionType) {
        Boolean flag = false;
        switch (optionType) {
            case 0:
                // 进行封号处理
                flag = userService.sealedAccount(userId);
                break;
            case 1:
                // 进行解除封号处理
                flag = userService.unblockTheAccount(userId);
                break;
            case 2:
                // 进行违规处理
                flag = userService.illegalAccount(userId);
                break;
            case 3:
                // 进行解除违规处理
                flag = userService.cancelTheIllegalAccount(userId);
                break;
        }
        return flag;
    }

    @Override
    public Long getNumberOfUserInfoAudit() {
        return userService.numberOfUsersAudit();
    }

    @Override
    public List<PagedUserVO> getUserInfoAuditList(PageInfoVO pageInfoVO) {
        // 获取待审核用户
        List<User> users = userService.queryUsersAuditByPageInfo(pageInfoVO);
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:getUserInfoAuditList:users: [{}]", JSON.toJSONString(users));
        }
        return userConverter.mapPOList2PagedUserVOList(users, pageInfoVO.getCurrentPage(), null);
    }

    @Override
    public UserVO getUserInfoById(Long userId) {
        User user = userService.queryById(userId);
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:getUserInfoById:user: [{}]", JSON.toJSONString(user));
        }
        return userConverter.mapPO2VO(user);
    }

    @Override
    public Boolean refuseUserInfoAudit(UserVO userVO) {
        Long userId = Long.valueOf(userVO.getKey());
        List<String> reason = userVO.getReason();
        String reviewInfo = userVO.getReviewInfo();
        User user = new User();
        user.setUserId(userId);
        user.setReviewInfo(reviewInfo);
        user.setInfoStatus(1);
        for (String type : reason) {
            if (("0").equals(type)) {
                // 头像不合格
                user.setAvatar("");
            } else if (("1").equals(type)) {
                // 个人简介不合格
                user.setInformation("");
            } else {
                // 用户呢称不合格
                user.setNickname("");
            }
        }
        User update = userService.update(user);
        return update.getInfoStatus() == 1;
    }

    @Override
    public Boolean infoAuditConfirm(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setInfoStatus(1);
        User update = userService.update(user);
        return update.getInfoStatus() == 1;
    }

    @Override
    public List<PagedUserVO> searchUser(String nickname, Integer userStatus, Long currentPage, Long pageSize) throws ExecutionException, InterruptedException {
        List<PagedUserVO> res = new ArrayList<>();
        // 1. 两个都有值
        if (nickname != null && nickname != "" && userStatus != null) {
            CompletableFuture<List<User>> userListFuture = CompletableFuture.supplyAsync(() -> {
                return userService.queryUsersByNicknameAndStatus(nickname, userStatus, currentPage, pageSize);
            }, userThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return userService.countUsersByNicknameAndStatus(nickname, userStatus);
            }, userThreadPool);
            res = userListFuture.thenCombine(countFuture, (userList, count) -> {
                if (userList != null) {
                    if (log.isInfoEnabled()) {
                        log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:searchUser:userList: [{}]", JSON.toJSONString(userList));
                        log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:searchUser:count: [{}]", JSON.toJSONString(count));
                    }
                    return userConverter.mapPOList2PagedUserVOList(userList, currentPage, count);
                } else {
                    return null;
                }
            }).get();
        } else if (nickname != null && nickname != "") {
            // 2. 没有用户状态, 根据用户昵称来搜索
            CompletableFuture<List<User>> userListFuture = CompletableFuture.supplyAsync(() -> {
                return userService.queryUsersByNickname(nickname, currentPage, pageSize);
            }, userThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return userService.countUsersByNickname(nickname);
            }, userThreadPool);
            res = userListFuture.thenCombine(countFuture, (userList, count) -> {
                if (userList == null || userList.isEmpty()) {
                    return null;
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:searchUser:userList: [{}]", JSON.toJSONString(userList));
                        log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:searchUser:count: [{}]", JSON.toJSONString(count));
                    }
                    return userConverter.mapPOList2PagedUserVOList(userList, currentPage, count);
                }
            }).get();
        } else if (userStatus != null) {
            // 3. 根据用户状态来搜索
            CompletableFuture<List<User>> userListFuture = CompletableFuture.supplyAsync(() -> {
                return userService.queryUsersByStatus(userStatus, currentPage, pageSize);
            }, userThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return userService.countUsersByStatus(userStatus);
            }, userThreadPool);
            res = userListFuture.thenCombine(countFuture, (userList, count) -> {
                if (userList == null) {
                    return null;
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:searchUser:userList: [{}]", JSON.toJSONString(userList));
                        log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:searchUser:count: [{}]", JSON.toJSONString(count));
                    }
                    return userConverter.mapPOList2PagedUserVOList(userList, currentPage, count);
                }
            }).get();
        }
        return res;
    }

    @Override
    public List<PagedUserVO> getSearchUserInfoAuditList(String nickname, Long currentPage, Long pageSize) {
        List<User> users = userService.queryAuditUsersByNickname(nickname, currentPage, pageSize);
        Long count = userService.countAuditUsersByNickname(nickname);
        if (log.isInfoEnabled()) {
            log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:getSearchUserInfoAuditList:userList: [{}]", JSON.toJSONString(users));
            log.info("chenyun-user:domain:service:impl:UserProfileServiceDomainImpl:getSearchUserInfoAuditList:count: [{}]", JSON.toJSONString(count));
        }
        return userConverter.mapPOList2PagedUserVOList(users, currentPage, count);
    }
}
