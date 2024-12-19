package com.programmercy.infra.mapper;

import com.programmercy.infra.po.UserPunishments;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户惩罚记录表(UserPunishments)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-17 09:48:05
 */
public interface UserPunishmentsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserPunishments queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param userPunishments 查询条件
     * @return 对象列表
     */
    List<UserPunishments> queryAllByLimit(UserPunishments userPunishments);

    /**
     * 统计总行数
     *
     * @param userPunishments 查询条件
     * @return 总行数
     */
    long count(UserPunishments userPunishments);

    /**
     * 新增数据
     *
     * @param userPunishments 实例对象
     * @return 影响行数
     */
    int insert(UserPunishments userPunishments);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserPunishments> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserPunishments> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserPunishments> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserPunishments> entities);

    /**
     * 修改数据
     *
     * @param userPunishments 实例对象
     * @return 影响行数
     */
    int update(UserPunishments userPunishments);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据用户 id，获取其惩罚记录
     * @param userId
     * @return
     */
    UserPunishments queryByUserId(@Param("userId") Long userId);
}

