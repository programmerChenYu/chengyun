package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.Roles;
import com.programmercy.infra.mapper.RolesDao;
import com.programmercy.infra.service.RolesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 角色表(Roles)表服务实现类
 *
 * @author makejava
 * @since 2024-12-17 09:25:07
 */
@Service("rolesService")
public class RolesServiceImpl implements RolesService {
    @Resource
    private RolesDao rolesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Roles queryById(Integer id) {
        return this.rolesDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    @Override
    public Roles insert(Roles roles) {
        this.rolesDao.insert(roles);
        return roles;
    }

    /**
     * 修改数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    @Override
    public Roles update(Roles roles) {
        this.rolesDao.update(roles);
        return this.queryById(roles.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.rolesDao.deleteById(id) > 0;
    }

    @Override
    public List<Integer> queryPermissions(Integer role) {
        return this.rolesDao.queryPermissions(role);
    }
}
