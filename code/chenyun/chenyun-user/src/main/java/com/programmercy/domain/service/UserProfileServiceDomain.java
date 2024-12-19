package com.programmercy.domain.service;

import com.programmercy.entity.Result;
import com.programmercy.vo.PageInfoVO;
import com.programmercy.vo.PagedUserVO;
import com.programmercy.vo.UserVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Description: 用户信息相关的服务
 * Created by 爱吃小鱼的橙子 on 2024-11-25 16:52
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface UserProfileServiceDomain {
    /**
     * 根据分页要求获取用户列表
     * @param pagedUserVO
     * @return
     */
    List<PagedUserVO> pageForAListOfUsers(PagedUserVO pagedUserVO);

    /**
     * 获取用户总数
     * @return
     */
    Long numberOfUsers();

    /**
     * 根据操作类型来修改用户的状态
     * 0：封号
     * 1：解除封号
     * 2：违规
     * 3：接触违规
     * @param userId
     * @param optionType
     * @return
     */
    Boolean userManagementOption(Long userId, Integer optionType);

    /**
     * 根据操作类型来修改用户的状态（批量操作版）
     * 0：封号
     * 1：解除封号
     * 2：违规
     * 3：接触违规
     * @param userIds
     * @param optionType
     * @return
     */
    Boolean userManagementOptionBatch(List<Long> userIds, Integer optionType);

    /**
     * 待审核用户总数
     * @return
     */
    Long getNumberOfUserInfoAudit();

    /**
     * 分页获取待审核用户列表
     * @param pageInfoVO
     * @return
     */
    List<PagedUserVO> getUserInfoAuditList(PageInfoVO pageInfoVO);

    /**
     * 根据 id, 查询指定用户的个人信息
     * @param userId
     * @return
     */
    UserVO getUserInfoById(Long userId);

    /**
     * 用户个人信息审核不通过
     * 0: 头像不合格
     * 1: 个人简介不合格
     * 2: 昵称不合格
     * @param userVO
     * @return
     */
    Boolean refuseUserInfoAudit(UserVO userVO);

    /**
     * 用户信息审核通过
     * @param userId
     * @return
     */
    Boolean infoAuditConfirm(Long userId);

    /**
     * 查询用户(管理页面)
     * @param nickname
     * @param userStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<PagedUserVO> searchUser(String nickname, Integer userStatus, Long currentPage, Long pageSize) throws ExecutionException, InterruptedException;

    /**
     * 查询用户（审核页面）
     * @param nickname
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<PagedUserVO> getSearchUserInfoAuditList(String nickname, Long currentPage, Long pageSize);

}
