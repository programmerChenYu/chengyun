package com.programmercy.infra.service;

import com.programmercy.infra.po.Location;

/**
 * 地理位置表(Location)表服务接口
 *
 * @author makejava
 * @since 2024-11-18 13:46:05
 */
public interface LocationService {

    /**
     * 通过ID查询单条数据
     *
     * @param locationId 主键
     * @return 实例对象
     */
    Location queryById(Long locationId);

    /**
     * 新增数据
     *
     * @param location 实例对象
     * @return 实例对象
     */
    Location insert(Location location);

    /**
     * 修改数据
     *
     * @param location 实例对象
     * @return 实例对象
     */
    Location update(Location location);

    /**
     * 通过主键删除数据
     *
     * @param locationId 主键
     * @return 是否成功
     */
    boolean deleteById(Long locationId);

}
