package com.programmercy.infra.service;

import com.programmercy.infra.po.User;
import com.programmercy.vo.PageInfoVO;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-14 14:32:04
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    User queryById(Long userId);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     * 查询所有用户
     * @return
     */
    List<User> queryAllUsers();

    /**
     * 根据分页信息，查询用户列表
     * @param currentPage 当前页码
     * @param pageSize 一页显示的内容多少
     * @return
     */
    List<User> queryUsersByPageInfo(Long currentPage, Long pageSize);

    /**
     * 获取用户总数，管理员除外
     * @return
     */
    Long numberOfUsers();

    /**
     * 对用户进行封号处理
     * @param userId
     * @return
     */
    Boolean sealedAccount(Long userId);

    /**
     * 解除用户的封号
     * @param userId
     * @return
     */
    Boolean unblockTheAccount(Long userId);

    /**
     * 对用户进行违规处理
     * @param userId
     * @return
     */
    Boolean illegalAccount(Long userId);

    /**
     * 解除用户的违规
     * @param userId
     * @return
     */
    Boolean cancelTheIllegalAccount(Long userId);

    /**
     * 对用户进行封号处理（批量版）
     * @param userIds
     * @return
     */
    Boolean sealedAccountBatch(List<Long> userIds);

    /**
     * 解除用户的封号（批量版）
     * @param userIds
     * @return
     */
    Boolean unblockTheAccountBatch(List<Long> userIds);

    /**
     * 对用户进行违规处理（批量版）
     * @param userIds
     * @return
     */
    Boolean illegalAccountBatch(List<Long> userIds);

    /**
     * 解除用户的违规（批量版）
     * @param userIds
     * @return
     */
    Boolean cancelTheIllegalAccountBatch(List<Long> userIds);

    /**
     * 根据传入id，查询用户状态列表
     * @param userIds
     * @return
     */
    List<Integer> queryUsersStatus(List<Long> userIds);

    /**
     * 获取待审核用户总数
     * @return
     */
    Long numberOfUsersAudit();

    /**
     * 根据分页信息查询待审核用户列表
     * @param pageInfoVO
     * @return
     */
    List<User> queryUsersAuditByPageInfo(PageInfoVO pageInfoVO);

    /**
     * 根据用户昵称和用户状态来搜索,昵称是模糊搜索
     * @param nickname
     * @param userStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<User> queryUsersByNicknameAndStatus(String nickname, Integer userStatus, Long currentPage, Long pageSize);

    /**
     * 根据用户ID和用户状态来精确搜索
     * @param userId
     * @param userStatus
     * @return
     */
    User queryUserByIdAndStatus(String userId, Integer userStatus);

    /**
     * 根据用户昵称来进行模糊搜索
     * @param nickname
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<User> queryUsersByNickname(String nickname, Long currentPage, Long pageSize);

    /**
     * 根据用户id进行精确搜索
     * @param userId
     * @return
     */
    User queryUserById(String userId);

    /**
     * 根据用户状态来搜索
     * @param userStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<User> queryUsersByStatus(Integer userStatus, Long currentPage, Long pageSize);

    /**
     * 根据用户名称和用户状态查看有多少符合的用户
     * @param nickname
     * @param userStatus
     * @return
     */
    Long countUsersByNicknameAndStatus(String nickname, Integer userStatus);

    /**
     * 根据用户名称查看有多少符合的用户
     * @param nickname
     * @return
     */
    Long countUsersByNickname(String nickname);

    /**
     * 根据用户状态查看有多少符合的用户
     * @param userStatus
     * @return
     */
    Long countUsersByStatus(Integer userStatus);

    /**
     * 根据用户昵称查询待审核用户列表
     * @param nickname
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<User> queryAuditUsersByNickname(String nickname, Long currentPage, Long pageSize);

    /**
     * 根据用户昵称查询待审核用户列表中用户总数
     * @param nickname
     * @return
     */
    Long countAuditUsersByNickname(String nickname);
}
