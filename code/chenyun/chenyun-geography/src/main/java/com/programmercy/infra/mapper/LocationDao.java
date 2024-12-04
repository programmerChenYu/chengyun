package com.programmercy.infra.mapper;

import com.programmercy.infra.po.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 地理位置表(Location)表数据库访问层
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-14 14:14:58
 */
@Mapper
public interface LocationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param locationId 主键
     * @return 实例对象
     */
    Location queryById(Long locationId);

    /**
     * 查询指定行数据
     *
     * @param location 查询条件
     * @return 对象列表
     */
    List<Location> queryAllByLimit(Location location);

    /**
     * 统计总行数
     *
     * @param location 查询条件
     * @return 总行数
     */
    long count(Location location);

    /**
     * 新增数据
     *
     * @param location 实例对象
     * @return 影响行数
     */
    int insert(Location location);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Location> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Location> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Location> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Location> entities);

    /**
     * 修改数据
     *
     * @param location 实例对象
     * @return 影响行数
     */
    int update(Location location);

    /**
     * 通过主键删除数据
     *
     * @param locationId 主键
     * @return 影响行数
     */
    int deleteById(Long locationId);

}

