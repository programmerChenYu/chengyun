package com.programmercy.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.UserAuthServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.vo.AuthVO;
import com.programmercy.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 用户认证控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-14 16:50
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@SuppressWarnings("unchecked")
public class UserAuthController {

    @Resource
    private UserAuthServiceDomain userAuthServiceDomain;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserVO userVO) {
        try {
            Preconditions.checkArgument(userVO.getUsername() != null && userVO.getUsername() != "" , ExceptionLanguageConstant.USER_NAME_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserAuthController:login:userVO: [{}]", JSON.toJSONString(userVO));
            }
            Preconditions.checkArgument(userVO.getPassword() != null && userVO.getPassword() != "", ExceptionLanguageConstant.PASSWORD_NULL_EXCEPTION);
            return Result.ok(userAuthServiceDomain.login(userVO.getUsername(), userVO.getPassword(), userVO.getClientType()));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserAuthController:login:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<Boolean> logout() {
        try {
            return Result.ok(userAuthServiceDomain.logout());
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserAuthController:logout:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 通过 token 获取登录的用户信息
     */
    @PostMapping("/getUserInfoByToken")
    public Result<UserVO> getUserInfoByToken(@RequestBody AuthVO authVO) {
        try {
            Preconditions.checkNotNull(authVO.getToken(), ExceptionLanguageConstant.TOKEN_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserAuthController:getUserInfoByToken:authVO: [{}]", JSON.toJSONString(authVO));
            }
            return Result.ok(userAuthServiceDomain.getUserInfoByToken(authVO.getToken()));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserAuthController:getUserInfoByToken:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }
}
