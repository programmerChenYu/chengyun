package com.programmercy.infra.service;

import com.programmercy.infra.po.BlogPost;
import com.programmercy.vo.PageInfoVO;

import java.util.List;

/**
 * 博客表(BlogPost)表服务接口
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-02 10:14:10
 */
public interface BlogPostService {

    /**
     * 通过ID查询单条数据
     *
     * @param blogPostId 主键
     * @return 实例对象
     */
    BlogPost queryById(Long blogPostId);

    /**
     * 新增数据
     *
     * @param blogPost 实例对象
     * @return 实例对象
     */
    BlogPost insert(BlogPost blogPost);

    /**
     * 修改数据
     *
     * @param blogPost 实例对象
     * @return 实例对象
     */
    BlogPost update(BlogPost blogPost);

    /**
     * 通过主键删除数据
     *
     * @param blogPostId 主键
     * @return 是否成功
     */
    boolean deleteById(Long blogPostId);

    /**
     * 分页查询博文列表
     * @param pageInfoVO
     * @return
     */
    List<BlogPost> pagingQueryArticleList(PageInfoVO pageInfoVO);

    /**
     * 博文总数
     * @return
     */
    Long blogPostCount();

    /**
     * 已审核博文总数
     * @return
     */
    Long auditedBlogPostCount();

    /**
     * 分页查询已审核文章
     * @param pageInfoVO
     * @return
     */
    List<BlogPost> pagingQueryAuditedArticleList(PageInfoVO pageInfoVO);

    /**
     * 分页查询待审核文章
     * @param pageInfoVO
     * @return
     */
    List<BlogPost> pagingQueryReviewedArticleList(PageInfoVO pageInfoVO);

    /**
     * 待审核文章总数
     * @return
     */
    Long reviewedBlogPostCount();

    /**
     * 分页搜索文章
     * @param title
     * @param status
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<BlogPost> pagingQueryArticleListByLimit(String title, String status, Long currentPage, Long pageSize);

    /**
     * 搜索到的博文总数
     * @return
     */
    Long searchBlogPostCount(String title, String status);
}
