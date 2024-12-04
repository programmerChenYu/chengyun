package com.programmercy.infra.mapper;

import com.programmercy.infra.po.BlogPostTag;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 博客标签关联表(BlogPostTag)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-03 16:01:33
 */
public interface BlogPostTagDao {

    /**
     * 通过ID查询单条数据
     *
     * @param blogPostId 主键
     * @return 实例对象
     */
    BlogPostTag queryById(Long blogPostId);

    /**
     * 查询指定行数据
     *
     * @param blogPostTag 查询条件
     * @return 对象列表
     */
    List<BlogPostTag> queryAllByLimit(BlogPostTag blogPostTag);

    /**
     * 统计总行数
     *
     * @param blogPostTag 查询条件
     * @return 总行数
     */
    long count(BlogPostTag blogPostTag);

    /**
     * 新增数据
     *
     * @param blogPostTag 实例对象
     * @return 影响行数
     */
    int insert(BlogPostTag blogPostTag);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BlogPostTag> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BlogPostTag> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BlogPostTag> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BlogPostTag> entities);

    /**
     * 修改数据
     *
     * @param blogPostTag 实例对象
     * @return 影响行数
     */
    int update(BlogPostTag blogPostTag);

    /**
     * 通过主键删除数据
     *
     * @param blogPostId 主键
     * @return 影响行数
     */
    int deleteById(Long blogPostId);

    /**
     * 获取所有标签在博文中出现的次数
     * @return
     */
    Long queryAllCount();

    /**
     * 根据标签id，去博客标签关联表中进行计数，看被多少博文引用
     * @param tagId
     * @return
     */
    Long countByTagId(@Param("tagId") Long tagId);
}

