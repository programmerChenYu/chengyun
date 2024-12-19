package com.programmercy.infra.service;

import com.programmercy.infra.po.Roles;

import java.util.List;

/**
 * 角色表(Roles)表服务接口
 *
 * @author makejava
 * @since 2024-12-17 09:25:07
 */
public interface RolesService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Roles queryById(Integer id);

    /**
     * 新增数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    Roles insert(Roles roles);

    /**
     * 修改数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    Roles update(Roles roles);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据角色获取具有的权限 id
     * @param role
     * @return
     */
    List<Integer> queryPermissions(Integer role);
}
