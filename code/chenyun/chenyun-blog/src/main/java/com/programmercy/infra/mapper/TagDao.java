package com.programmercy.infra.mapper;

import com.programmercy.infra.po.Tag;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 标签表(Tag)表数据库访问层
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-29 09:00:56
 */
public interface TagDao {

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    Tag queryById(Long tagId);

    /**
     * 查询指定行数据
     *
     * @param tag 查询条件
     * @return 对象列表
     */
    List<Tag> queryAllByLimit(@Param("tag") Tag tag,
                              @Param("target") Long target,
                              @Param("pageSize") Long pageSize);

    /**
     * 统计总行数
     *
     * @param tag 查询条件
     * @return 总行数
     */
    long count(Tag tag);

    /**
     * 新增数据
     *
     * @param tag 实例对象
     * @return 影响行数
     */
    int insert(Tag tag);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Tag> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Tag> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Tag> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Tag> entities);

    /**
     * 修改数据
     *
     * @param tag 实例对象
     * @return 影响行数
     */
    int update(Tag tag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 影响行数
     */
    int deleteById(Long tagId);

    /**
     * 批量停用标签
     * @param tagIds
     * @return
     */
    Long batchDeactivate(@Param("tagIds") List<Long> tagIds);

    /**
     * 批量启用标签
     * @param tagIds
     * @return
     */
    Long batchEnable(@Param("tagIds") List<Long> tagIds);

    /**
     * 模糊搜索标签
     * @param tag
     * @param target
     * @param pageSize
     * @return
     */
    List<Tag> querySearchByLimit(@Param("tag") Tag tag,
                                 @Param("target") long target,
                                 @Param("pageSize") Long pageSize);

    /**
     * 搜索的标签总数
     * @param tag
     * @return
     */
    Long searchCount(@Param("tag") Tag tag);

    /**
     * 获取所有的标签
     * @return
     */
    List<Tag> queryAllTag();
}

