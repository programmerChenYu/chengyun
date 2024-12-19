package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.User;
import com.programmercy.infra.mapper.UserDao;
import com.programmercy.infra.service.UserService;
import com.programmercy.vo.PageInfoVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-14 14:32:04
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User queryByUsernameAndPassword(String username, String password) {
        return userDao.queryByUsernameAndPassword(username, password);
    }

    @Override
    public Long countAuditUsersByNickname(String nickname) {
        return userDao.countAuditUsersByNickname(nickname);
    }

    @Override
    public List<User> queryAuditUsersByNickname(String nickname, Long currentPage, Long pageSize) {
        return userDao.queryAuditUsersByNickname(nickname, (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public Long countUsersByStatus(Integer userStatus) {
        return userDao.countUsersByStatus(userStatus);
    }

    @Override
    public Long countUsersByNickname(String nickname) {
        return userDao.countUsersByNickname(nickname);
    }

    @Override
    public Long countUsersByNicknameAndStatus(String nickname, Integer userStatus) {
        return userDao.countUsersByNicknameAndStatus(nickname, userStatus);
    }

    @Override
    public List<User> queryUsersByStatus(Integer userStatus, Long currentPage, Long pageSize) {
        return userDao.queryUsersByStatus(userStatus, (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public User queryUserById(String userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public List<User> queryUsersByNickname(String nickname, Long currentPage, Long pageSize) {
        return userDao.queryUsersByNickname(nickname, (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public User queryUserByIdAndStatus(String userId, Integer userStatus) {
        return userDao.queryUserByIdAndStatus(userId, userStatus);
    }

    @Override
    public List<User> queryUsersByNicknameAndStatus(String nickname, Integer userStatus, Long currentPage, Long pageSize) {
        return userDao.queryUsersByNicknameAndStatus(nickname, userStatus, (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public List<User> queryUsersAuditByPageInfo(PageInfoVO pageInfoVO) {
        return userDao.queryUsersAuditByPageInfo((pageInfoVO.getCurrentPage()-1)*pageInfoVO.getPageSize(), pageInfoVO.getPageSize());
    }

    @Override
    public Long numberOfUsersAudit() {
        User user = new User();
        user.setInfoStatus(0);
        user.setRole(2);
        return userDao.count(user);
    }

    @Override
    public List<Integer> queryUsersStatus(List<Long> userIds) {
        return userDao.queryUsers(userIds);
    }

    @Override
    public Boolean sealedAccountBatch(List<Long> userIds) {
        User user = new User();
        user.setUserStatus(0);
        return userDao.updateBatch(userIds, user) == userIds.size();
    }

    @Override
    public Boolean unblockTheAccountBatch(List<Long> userIds) {
        User user = new User();
        user.setUserStatus(2);
        return userDao.updateBatch(userIds, user) == userIds.size();
    }

    @Override
    public Boolean illegalAccountBatch(List<Long> userIds) {
        User user = new User();
        user.setUserStatus(1);
        return userDao.updateBatch(userIds, user) == userIds.size();
    }

    @Override
    public Boolean cancelTheIllegalAccountBatch(List<Long> userIds) {
        User user = new User();
        user.setUserStatus(2);
        return userDao.updateBatch(userIds, user) == userIds.size();
    }

    @Override
    public Boolean sealedAccount(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(0);
        return userDao.update(user) > 0;
    }

    @Override
    public Boolean unblockTheAccount(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(2);
        return userDao.update(user) > 0;
    }

    @Override
    public Boolean illegalAccount(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(1);
        return userDao.update(user) > 0;
    }

    @Override
    public Boolean cancelTheIllegalAccount(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(2);
        return userDao.update(user) > 0;
    }

    @Override
    public Long numberOfUsers() {
        User user = new User();
        user.setRole(2);
        return userDao.count(user);
    }

    @Override
    public List<User> queryAllUsers() {
        return userDao.queryAll();
    }

    /**
     * 根据分页信息，查询用户列表
     * @param currentPage 当前页码
     * @param pageSize 一页显示的内容多少
     * @return
     */
    @Override
    public List<User> queryUsersByPageInfo(Long currentPage, Long pageSize) {
        return userDao.queryUsersByPageInfo((currentPage-1)*pageSize, pageSize);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Long userId) {
        return this.userDao.queryById(userId);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.userDao.deleteById(userId) > 0;
    }
}
