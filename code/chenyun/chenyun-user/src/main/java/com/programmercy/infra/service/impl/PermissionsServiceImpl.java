package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.Permissions;
import com.programmercy.infra.mapper.PermissionsDao;
import com.programmercy.infra.service.PermissionsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限信息表(Permissions)表服务实现类
 *
 * @author makejava
 * @since 2024-12-17 09:27:02
 */
@Service("permissionsService")
public class PermissionsServiceImpl implements PermissionsService {
    @Resource
    private PermissionsDao permissionsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Permissions queryById(Integer id) {
        return this.permissionsDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param permissions 实例对象
     * @return 实例对象
     */
    @Override
    public Permissions insert(Permissions permissions) {
        this.permissionsDao.insert(permissions);
        return permissions;
    }

    /**
     * 修改数据
     *
     * @param permissions 实例对象
     * @return 实例对象
     */
    @Override
    public Permissions update(Permissions permissions) {
        this.permissionsDao.update(permissions);
        return this.queryById(permissions.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.permissionsDao.deleteById(id) > 0;
    }

    @Override
    public List<Permissions> queryByIds(List<Integer> permissionsIds) {
        return this.permissionsDao.queryByIds(permissionsIds);
    }
}
