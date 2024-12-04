package com.programmercy.domain.service;

import com.programmercy.vo.BlogPostVO;
import com.programmercy.vo.PageInfoVO;
import com.programmercy.vo.PagingQuerySearchBlogPostVO;

import java.util.List;

/**
 * Description: 博文的服务类
 * Created by 爱吃小鱼的橙子 on 2024-12-02 10:19
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface BlogPostServiceDomain {

    /**
     * 分页查询博文列表
     * @param pageInfoVO
     * @return
     */
    List<BlogPostVO> pagingQueryArticleList(PageInfoVO pageInfoVO);

    /**
     * 博文总数
     * @return
     */
    Long blogPostCount();

    /**
     * 分页查询已审核文章
     * @param pageInfoVO
     * @return
     */
    List<BlogPostVO> pagingQueryAuditedArticleList(PageInfoVO pageInfoVO);

    /**
     * 已审核文章总数
     * @return
     */
    Long auditedBlogPostCount();

    /**
     * 分页查询待审核文章
     * @param pageInfoVO
     * @return
     */
    List<BlogPostVO> pagingQueryReviewedArticleList(PageInfoVO pageInfoVO);

    /**
     * 待审核文章总数
     * @return
     */
    Long reviewedBlogPostCount();

    /**
     * 分页搜索文章
     * @param pagingQuerySearchBlogPostVO
     * @return
     */
    List<BlogPostVO> pagingQuerySearchArticleList(PagingQuerySearchBlogPostVO pagingQuerySearchBlogPostVO);

    /**
     * 搜索到的博文总数
     * @return
     */
    Long searchBlogPostCount(String title, String status);

    /**
     * 下架文章
     * @param blogPostId
     * @return
     */
    Boolean articleRemoval(Long blogPostId);

    /**
     * 根据 ID 获取博文详细信息
     * @param blogPostId
     * @return
     */
    BlogPostVO getBlogInfo(Long blogPostId);

    /**
     * 文章审核不通过
     * @param blogPostId
     * @return
     */
    Boolean blogInfoRefuse(Long blogPostId);

    /**
     * 文章审核通过
     * @param blogPostId
     * @return
     */
    Boolean blogInfoProcess(Long blogPostId);
}
