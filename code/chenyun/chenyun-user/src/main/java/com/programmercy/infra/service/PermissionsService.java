package com.programmercy.infra.service;

import com.programmercy.infra.po.Permissions;

import java.util.List;

/**
 * 权限信息表(Permissions)表服务接口
 *
 * @author makejava
 * @since 2024-12-17 09:27:02
 */
public interface PermissionsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Permissions queryById(Integer id);

    /**
     * 新增数据
     *
     * @param permissions 实例对象
     * @return 实例对象
     */
    Permissions insert(Permissions permissions);

    /**
     * 修改数据
     *
     * @param permissions 实例对象
     * @return 实例对象
     */
    Permissions update(Permissions permissions);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据 id 获取权限
     * @param permissionsIds
     * @return
     */
    List<Permissions> queryByIds(List<Integer> permissionsIds);
}
