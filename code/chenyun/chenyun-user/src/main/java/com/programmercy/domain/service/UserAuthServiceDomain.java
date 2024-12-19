package com.programmercy.domain.service;

import com.programmercy.vo.UserVO;

import java.util.concurrent.ExecutionException;

/**
 * Description: 用户认证服务
 * Created by 爱吃小鱼的橙子 on 2024-12-16 11:47
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface UserAuthServiceDomain {

    /**
     * 用户登录
     * @param username
     * @param password
     * @param clientType
     * @return
     */
    String login(String username, String password, Integer clientType) throws ExecutionException, InterruptedException;

    /**
     * 通过 token 获取登录的用户信息
     * @param token
     * @return
     */
    UserVO getUserInfoByToken(String token);

    /**
     * 退出登录
     * @return
     */
    Boolean logout();
}
