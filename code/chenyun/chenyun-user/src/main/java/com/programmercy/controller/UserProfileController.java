package com.programmercy.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.UserProfileServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.util.RsaEncodeUtil;
import com.programmercy.vo.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

/**
 * Description: 用户个人资料控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-14 16:18
 * Created with IntelliJ IDEA.
 *
 * @author 爱吃小鱼的橙子
 */
@Slf4j
@RequestMapping("/userProfile")
@RestController
@SuppressWarnings("unchecked")
public class UserProfileController {

    @Resource
    UserProfileServiceDomain userProfileServiceDomain;

    @Resource
    RsaEncodeUtil rsaEncodeUtil;

    /**
     * 分页获取所有用户
     */
    @PostMapping("/pageForAListOfUsers")
    public Result<List<PagedUserVO>> pageForAListOfUsers(@RequestBody PagedUserVO pagedUserVO) {
        try {
            Preconditions.checkNotNull(pagedUserVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
            Preconditions.checkArgument(pagedUserVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            Preconditions.checkArgument(pagedUserVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:pageForAListOfUsers:pagedUserVO: [{}]", JSON.toJSONString(pagedUserVO));
            }
            // 根据分页要求获取用户列表数据
            return Result.ok(userProfileServiceDomain.pageForAListOfUsers(pagedUserVO));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:pageForAListOfUsers:Exception: [{},{}]", e.getMessage(),e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 获取用户总数，排除管理员
     */
    @GetMapping("/numberOfUsers")
    public Result<Long> numberOfUsers() {
        try {
            return Result.ok(userProfileServiceDomain.numberOfUsers());
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:numberOfUsers:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 用户状态改变
     * 0：封号
     * 1：解除封号
     * 2：违规
     * 3：接触违规
     */
    @PostMapping("/userManagementOption")
    public Result<Boolean> userManagementOption(@RequestBody UserManagementOptionVO userManagementOptionVO) {
        try {
            Preconditions.checkNotNull(userManagementOptionVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:userManagementOption:userManagementOptionVO: [{}]", JSON.toJSONString(userManagementOptionVO));
            }
            return Result.ok(userProfileServiceDomain.userManagementOption(userManagementOptionVO.getUserId(), userManagementOptionVO.getOptionType()));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:userManagementOption:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 批量操作的用户状态改变
     */
    @PostMapping("/userManagementOptionBatch")
    public Result<Boolean> userManagementOptionBatch(@RequestBody UserManagementOptionBatchVO userManagementOptionBatchVO) {
        try {
            Preconditions.checkNotNull(userManagementOptionBatchVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:userManagementOptionBatch:userManagementOptionBatchVO: [{}]", JSON.toJSONString(userManagementOptionBatchVO));
            }
            return Result.ok(userProfileServiceDomain.userManagementOptionBatch(userManagementOptionBatchVO.getUserIds(), userManagementOptionBatchVO.getOptionType()));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:userManagementOptionBatch:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 待审核用户总数
     */
    @GetMapping("/getNumberOfUserInfoAudit")
    public Result<Long> getNumberOfUserInfoAudit() {
        try {
            return Result.ok(userProfileServiceDomain.getNumberOfUserInfoAudit());
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:getNumberOfUserInfoAudit:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 分页获取待审核用户列表
     */
    @PostMapping("/getUserInfoAuditList")
    public Result<PagedUserVO> getUserInfoAuditList(@RequestBody PageInfoVO pageInfoVO) {
        try {
            Preconditions.checkNotNull(pageInfoVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:getUserInfoAuditList:pageInfoVO: [{}]", JSON.toJSONString(pageInfoVO));
            }
            return Result.ok(userProfileServiceDomain.getUserInfoAuditList(pageInfoVO));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:getUserInfoAuditList:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 根据 id, 查询指定用户个人信息
     */
    @PostMapping("/getUserInfoById")
    public Result<UserVO> getUserInfoById(@RequestBody UserVO userVO) {
        try {
            byte[] decodeUserId = Base64.getDecoder().decode(userVO.getKey());
            byte[] decodeUserIdByte = rsaEncodeUtil.encodeRsaMessage(decodeUserId);
            Long userId = Long.valueOf(new String(decodeUserIdByte, "UTF-8"));
            Preconditions.checkNotNull(userVO.getKey(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:getUserInfoById:userVO: [{}]", JSON.toJSONString(userVO));
            }
            return Result.ok(userProfileServiceDomain.getUserInfoById(userId));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:getUserInfoById:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 用户个人信息审核不通过
     */
    @PostMapping("/refuseUserInfoAudit")
    public Result<Boolean> refuseUserInfoAudit(@RequestBody UserVO userVO) {
        try {
            Preconditions.checkNotNull(userVO.getKey(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:refuseUserInfoAudit:userVO: [{}]", JSON.toJSONString(userVO));
            }
            return Result.ok(userProfileServiceDomain.refuseUserInfoAudit(userVO));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:refuseUserInfoAudit:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 用户个人信息审核通过
     */
    @PostMapping("/infoAuditConfirm")
    public Result<Boolean> infoAuditConfirm(@RequestBody UserVO userVO) {
        try {
            Preconditions.checkNotNull(userVO.getKey(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:infoAuditConfirm:userVO: [{}]", JSON.toJSONString(userVO));
            }
            return Result.ok(userProfileServiceDomain.infoAuditConfirm(Long.valueOf(userVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:infoAuditConfirm:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 搜索用户(管理页面)
     */
    @PostMapping("/searchUser")
    public Result<List<PagedUserVO>> searchUser(@RequestBody SearchUserVO searchUserVO) {
        try {
            Preconditions.checkArgument(searchUserVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(searchUserVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:searchUser:searchUserVO: [{}]", JSON.toJSONString(searchUserVO));
            }
            if (searchUserVO.getSearchValue() != null) {
                byte[] decodeSearchValue = Base64.getDecoder().decode(searchUserVO.getSearchValue());
                byte[] searchValueByte = rsaEncodeUtil.encodeRsaMessage(decodeSearchValue);
                String searchValue = new String(searchValueByte, "UTF-8");
                searchUserVO.setSearchValue(searchValue);
            }
            Integer statusValue = null;
            if (searchUserVO.getStatusValue() != null) {
                byte[] decodeStatusValue = Base64.getDecoder().decode(searchUserVO.getStatusValue());
                byte[] statusValueByte = rsaEncodeUtil.encodeRsaMessage(decodeStatusValue);
                String statusValueStr = new String(statusValueByte, "UTF-8");
                if (!"undefined".equals(statusValueStr)) {
                    statusValue = Integer.valueOf(statusValueStr);
                }
            }
            return Result.ok(userProfileServiceDomain.searchUser(searchUserVO.getSearchValue(), statusValue,
                    searchUserVO.getCurrentPage(), searchUserVO.getPageSize()));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:searchUser:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 根据用户昵称搜索用户（审核页面）
     */
    @PostMapping("/getSearchUserInfoAuditList")
    public Result<List<PagedUserVO>> getSearchUserInfoAuditList(@RequestBody SearchUserVO searchUserVO) {
        try {
            if (searchUserVO.getSearchValue() != null) {
                byte[] decodeSearchValue = Base64.getDecoder().decode(searchUserVO.getSearchValue());
                byte[] searchValueByte = rsaEncodeUtil.encodeRsaMessage(decodeSearchValue);
                String searchValue = new String(searchValueByte, "UTF-8");
                searchUserVO.setSearchValue(searchValue);
            }
            Preconditions.checkNotNull(searchUserVO.getSearchValue(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
            Preconditions.checkArgument(searchUserVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            Preconditions.checkArgument(searchUserVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-user:controller:UserProfileController:getSearchUserInfoAuditList:searchUserVO: [{}]", JSON.toJSONString(searchUserVO));
            }
            return Result.ok(userProfileServiceDomain.getSearchUserInfoAuditList(searchUserVO.getSearchValue(), searchUserVO.getCurrentPage(), searchUserVO.getPageSize()));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserProfileController:getSearchUserInfoAuditList:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }
}
