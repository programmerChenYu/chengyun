package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.UserPunishments;
import com.programmercy.infra.mapper.UserPunishmentsDao;
import com.programmercy.infra.service.UserPunishmentsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户惩罚记录表(UserPunishments)表服务实现类
 *
 * @author makejava
 * @since 2024-12-17 09:48:05
 */
@Service("userPunishmentsService")
public class UserPunishmentsServiceImpl implements UserPunishmentsService {
    @Resource
    private UserPunishmentsDao userPunishmentsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserPunishments queryById(Long id) {
        return this.userPunishmentsDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param userPunishments 实例对象
     * @return 实例对象
     */
    @Override
    public UserPunishments insert(UserPunishments userPunishments) {
        this.userPunishmentsDao.insert(userPunishments);
        return userPunishments;
    }

    /**
     * 修改数据
     *
     * @param userPunishments 实例对象
     * @return 实例对象
     */
    @Override
    public UserPunishments update(UserPunishments userPunishments) {
        this.userPunishmentsDao.update(userPunishments);
        return this.queryById(userPunishments.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userPunishmentsDao.deleteById(id) > 0;
    }

    @Override
    public UserPunishments queryByUserId(Long userId) {
        return this.userPunishmentsDao.queryByUserId(userId);
    }
}
