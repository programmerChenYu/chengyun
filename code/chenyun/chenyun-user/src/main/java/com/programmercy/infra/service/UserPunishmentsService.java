package com.programmercy.infra.service;

import com.programmercy.infra.po.UserPunishments;

/**
 * 用户惩罚记录表(UserPunishments)表服务接口
 *
 * @author makejava
 * @since 2024-12-17 09:48:05
 */
public interface UserPunishmentsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserPunishments queryById(Long id);

    /**
     * 新增数据
     *
     * @param userPunishments 实例对象
     * @return 实例对象
     */
    UserPunishments insert(UserPunishments userPunishments);

    /**
     * 修改数据
     *
     * @param userPunishments 实例对象
     * @return 实例对象
     */
    UserPunishments update(UserPunishments userPunishments);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据用户 id 获取该用户的惩罚记录
     * @param userId
     * @return
     */
    UserPunishments queryByUserId(Long userId);
}
