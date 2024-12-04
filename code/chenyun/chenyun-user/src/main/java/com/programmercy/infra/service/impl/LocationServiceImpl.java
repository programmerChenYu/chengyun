package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.Location;
import com.programmercy.infra.mapper.LocationDao;
import com.programmercy.infra.service.LocationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 地理位置表(Location)表服务实现类
 *
 * @author makejava
 * @since 2024-11-18 13:46:05
 */
@Service("locationService")
public class LocationServiceImpl implements LocationService {
    @Resource
    private LocationDao locationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param locationId 主键
     * @return 实例对象
     */
    @Override
    public Location queryById(Long locationId) {
        return this.locationDao.queryById(locationId);
    }

    /**
     * 新增数据
     *
     * @param location 实例对象
     * @return 实例对象
     */
    @Override
    public Location insert(Location location) {
        this.locationDao.insert(location);
        return location;
    }

    /**
     * 修改数据
     *
     * @param location 实例对象
     * @return 实例对象
     */
    @Override
    public Location update(Location location) {
        this.locationDao.update(location);
        return this.queryById(location.getLocationId());
    }

    /**
     * 通过主键删除数据
     *
     * @param locationId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long locationId) {
        return this.locationDao.deleteById(locationId) > 0;
    }
}
