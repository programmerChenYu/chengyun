package com.programmercy.infra.mapper;

import com.programmercy.infra.po.BlogPost;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 博客表(BlogPost)表数据库访问层
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-02 10:14:09
 */
public interface BlogPostDao {

    /**
     * 通过ID查询单条数据
     *
     * @param blogPostId 主键
     * @return 实例对象
     */
    BlogPost queryById(Long blogPostId);

    /**
     * 查询指定行数据
     * @param blogPost
     * @param target
     * @param pageSize
     * @return
     */
    List<BlogPost> queryAllByLimit(@Param("blogPost") BlogPost blogPost,
                                   @Param("target") Long target,
                                   @Param("pageSize") Long pageSize);

    /**
     * 统计总行数
     *
     * @param blogPost 查询条件
     * @return 总行数
     */
    long count(BlogPost blogPost);

    /**
     * 新增数据
     *
     * @param blogPost 实例对象
     * @return 影响行数
     */
    int insert(BlogPost blogPost);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BlogPost> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BlogPost> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BlogPost> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BlogPost> entities);

    /**
     * 修改数据
     *
     * @param blogPost 实例对象
     * @return 影响行数
     */
    int update(BlogPost blogPost);

    /**
     * 通过主键删除数据
     *
     * @param blogPostId 主键
     * @return 影响行数
     */
    int deleteById(Long blogPostId);

    /**
     * 分页搜索文章
     * @param blogPost
     * @param target
     * @param pageSize
     * @return
     */
    List<BlogPost> pagingQueryArticleListByLimit(@Param("blogPost") BlogPost blogPost,
                                                 @Param("target") Long target,
                                                 @Param("pageSize") Long pageSize);

    /**
     * 查询到的博文总数
     * @param blogPost
     * @return
     */
    Long searchBlogPostCount(@Param("blogPost") BlogPost blogPost);
}

