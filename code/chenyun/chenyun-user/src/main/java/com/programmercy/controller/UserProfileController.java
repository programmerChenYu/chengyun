package com.programmercy.controller;

import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.UserProfileServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.util.RsaEncodeUtil;
import com.programmercy.vo.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

/**
 * Description: 用户个人资料控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-14 16:18
 * Created with IntelliJ IDEA.
 *
 * @author 爱吃小鱼的橙子
 */
@RestController
public class UserProfileController {

    @Resource
    UserProfileServiceDomain userProfileServiceDomain;

    @Resource
    RsaEncodeUtil rsaEncodeUtil;

    /**
     * 分页获取所有用户
     */
    @PostMapping("/userProfile/pageForAListOfUsers")
    public Result<List<PagedUserVO>> pageForAListOfUsers(@RequestBody PagedUserVO pagedUserVO) throws InterruptedException {
        Preconditions.checkNotNull(pagedUserVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
        Preconditions.checkArgument(pagedUserVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
        Preconditions.checkArgument(pagedUserVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
        // 根据分页要求获取用户列表数据
        List<PagedUserVO> res = userProfileServiceDomain.pageForAListOfUsers(pagedUserVO);
        return Result.ok(res);
    }

    /**
     * 获取用户总数，排除管理员
     */
    @GetMapping("/userProfile/numberOfUsers")
    public Result<Long> numberOfUsers() {
        return Result.ok(userProfileServiceDomain.numberOfUsers());
    }

    /**
     * 用户状态改变
     * 0：封号
     * 1：解除封号
     * 2：违规
     * 3：接触违规
     */
    @PostMapping("/userProfile/userManagementOption")
    public Result<Boolean> userManagementOption(@RequestBody UserManagementOptionVO userManagementOptionVO) {
        Preconditions.checkNotNull(userManagementOptionVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
        return Result.ok(userProfileServiceDomain.userManagementOption(userManagementOptionVO.getUserId(), userManagementOptionVO.getOptionType()));
    }

    /**
     * 批量操作的用户状态改变
     */
    @PostMapping("/userProfile/userManagementOptionBatch")
    public Result<Boolean> userManagementOptionBatch(@RequestBody UserManagementOptionBatchVO userManagementOptionBatchVO) {
        Preconditions.checkNotNull(userManagementOptionBatchVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
        return Result.ok(userProfileServiceDomain.userManagementOptionBatch(userManagementOptionBatchVO.getUserIds(), userManagementOptionBatchVO.getOptionType()));
    }

    /**
     * 待审核用户总数
     */
    @GetMapping("/userProfile/getNumberOfUserInfoAudit")
    public Result<Long> getNumberOfUserInfoAudit() {
        return Result.ok(userProfileServiceDomain.getNumberOfUserInfoAudit());
    }

    /**
     * 分页获取单审核用户列表
     */
    @PostMapping("/userProfile/getUserInfoAuditList")
    public Result<PagedUserVO> getUserInfoAuditList(@RequestBody PageInfoVO pageInfoVO) {
        Preconditions.checkNotNull(pageInfoVO, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
        Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
        Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
        List<PagedUserVO> res = userProfileServiceDomain.getUserInfoAuditList(pageInfoVO);
        return Result.ok(res);
    }

    /**
     * 根据 id, 查询指定用户个人信息
     */
    @PostMapping("/userProfile/getUserInfoById")
    public Result<UserVO> getUserInfoById(@RequestBody UserVO userVO) throws UnsupportedEncodingException {
        String key = userVO.getKey();
        byte[] decodeUserId = Base64.getDecoder().decode(userVO.getKey());
        byte[] decodeUserIdByte = rsaEncodeUtil.encodeRsaMessage(decodeUserId);
        Long userId = Long.valueOf(new String(decodeUserIdByte, "UTF-8"));
        Preconditions.checkNotNull(key, ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
        return Result.ok(userProfileServiceDomain.getUserInfoById(userId));
    }

    /**
     * 用户个人信息审核不通过
     */
    @PostMapping("/userProfile/refuseUserInfoAudit")
    public Result<Boolean> refuseUserInfoAudit(@RequestBody UserVO userVO) {
        Preconditions.checkNotNull(userVO.getKey(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
        return Result.ok(userProfileServiceDomain.refuseUserInfoAudit(userVO));
    }

    /**
     * 用户个人信息审核通过
     */
    @PostMapping("/userProfile/infoAuditConfirm")
    public Result<Boolean> infoAuditConfirm(@RequestBody UserVO userVO) {
        Preconditions.checkNotNull(userVO.getKey(), ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
        return Result.ok(userProfileServiceDomain.infoAuditConfirm(Long.valueOf(userVO.getKey())));
    }

    /**
     * 搜索用户(管理页面)
     */
    @PostMapping("/userProfile/searchUser")
    public Result<List<PagedUserVO>> searchUser(@RequestBody SearchUserVO searchUserVO) throws UnsupportedEncodingException {

        byte[] decodeSearchValue = Base64.getDecoder().decode(searchUserVO.getSearchValue());
        byte[] decodeStatusValue = Base64.getDecoder().decode(searchUserVO.getStatusValue());
        byte[] searchValueByte = rsaEncodeUtil.encodeRsaMessage(decodeSearchValue);
        byte[] statusValueByte = rsaEncodeUtil.encodeRsaMessage(decodeStatusValue);
        String searchValue = new String(searchValueByte, "UTF-8");
        Integer statusValue = Integer.valueOf(new String(statusValueByte, "UTF-8"));
        return Result.ok(userProfileServiceDomain.searchUser(searchValue, statusValue,
                searchUserVO.getCurrentPage(), searchUserVO.getPageSize()));
    }

    /**
     * 根据用户昵称搜索用户（审核页面）
     */
    @PostMapping("/userProfile/getSearchUserInfoAuditList")
    public Result<List<PagedUserVO>> getSearchUserInfoAuditList(@RequestBody SearchUserVO searchUserVO) throws UnsupportedEncodingException {
        byte[] decodeSearchValue = Base64.getDecoder().decode(searchUserVO.getSearchValue());
        byte[] searchValueByte = rsaEncodeUtil.encodeRsaMessage(decodeSearchValue);
        String searchValue = new String(searchValueByte, "UTF-8");
        Preconditions.checkNotNull(searchValue, ExceptionLanguageConstant.USER_ID_NULL_EXCEPTION);
        Preconditions.checkArgument(searchUserVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
        Preconditions.checkArgument(searchUserVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
        return Result.ok(userProfileServiceDomain.getSearchUserInfoAuditList(searchValue, searchUserVO.getCurrentPage(), searchUserVO.getPageSize()));
    }

}
