package com.programmercy.controller;

import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.BlogPostServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.util.RsaEncodeUtil;
import com.programmercy.vo.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

/**
 * Description: 博文控制类
 * Created by 爱吃小鱼的橙子 on 2024-12-02 10:05
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Resource
    private BlogPostServiceDomain blogPostServiceDomain;

    @Resource
    private RsaEncodeUtil rsaEncodeUtil;

    /**
     * 分页查询博文列表
     */
    @PostMapping("/pagingQueryArticleList")
    public Result<List<PagingQueryBlogPostVO>> pagingQueryArticleList(@RequestBody PageInfoVO pageInfoVO) {
        try {
            Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            log.info("chenyun-blog:ArticleController:pagingQueryArticleList:pageInfoVO:【{}】", pageInfoVO);
            List<BlogPostVO> blogPostVOList = blogPostServiceDomain.pagingQueryArticleList(pageInfoVO);
            Long count = blogPostServiceDomain.blogPostCount();
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
            pagingQueryBlogPostVO.setCountOfPage(count);
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:pagingQueryArticleList:Exception: 【{}】", e.getMessage());
            return Result.fail(null);
        }
    }

    /**
     * 分页查询已审核文章
     */
    @PostMapping("/pagingQueryAuditedArticleList")
    public Result<PagingQueryBlogPostVO> pagingQueryAuditedArticleList(@RequestBody PageInfoVO pageInfoVO) {
        try {
            Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            log.info("chenyun-blog:ArticleController:pagingQueryAuditedArticleList:pageInfoVO:【{}】", pageInfoVO);
            List<BlogPostVO> blogPostVOList = blogPostServiceDomain.pagingQueryAuditedArticleList(pageInfoVO);
            Long count = blogPostServiceDomain.auditedBlogPostCount();
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
            pagingQueryBlogPostVO.setCountOfPage(count);
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:pagingQueryAuditedArticleList:Exception: 【{}】", e.getMessage());
            return Result.fail(null);
        }
    }

    /**
     * 分页查询待审核文章
     */
    @PostMapping("/pagingQueryReviewedArticleList")
    public Result<PagingQueryBlogPostVO> pagingQueryReviewedArticleList(@RequestBody PageInfoVO pageInfoVO) {
        try {
            Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            log.info("chenyun-blog:ArticleController:pagingQueryReviewedArticleList:pageInfoVO:【{}】", pageInfoVO);
            List<BlogPostVO> blogPostVOList = blogPostServiceDomain.pagingQueryReviewedArticleList(pageInfoVO);
            Long count = blogPostServiceDomain.reviewedBlogPostCount();
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
            pagingQueryBlogPostVO.setCountOfPage(count);
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:pagingQueryReviewedArticleList:Exception: 【{}】", e.getMessage());
            return Result.fail(null);
        }
    }

    /**
     * 分页搜索博文
     */
    @PostMapping("/pagingQuerySearchArticleList")
    public Result<PagingQueryBlogPostVO> pagingQuerySearchArticleList(@RequestBody PagingQuerySearchBlogPostVO pagingQuerySearchBlogPostVO) {
        try {
            Preconditions.checkArgument(pagingQuerySearchBlogPostVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(pagingQuerySearchBlogPostVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            Preconditions.checkArgument((pagingQuerySearchBlogPostVO.getTitle() != null && pagingQuerySearchBlogPostVO.getTitle() != "") ||
                    (pagingQuerySearchBlogPostVO.getStatus() != null && pagingQuerySearchBlogPostVO.getStatus() != ""),
                    ExceptionLanguageConstant.SEARCH_ARTICLE_TITLE_AND_STATUS_NULL_EXCEPTION);
            if (pagingQuerySearchBlogPostVO.getTitle() != null && pagingQuerySearchBlogPostVO.getTitle() != "") {
                byte[] decodeTitle = Base64.getDecoder().decode(pagingQuerySearchBlogPostVO.getTitle());
                byte[] titleByte = rsaEncodeUtil.encodeRsaMessage(decodeTitle);
                pagingQuerySearchBlogPostVO.setTitle(new String(titleByte, "UTF-8"));
            }
            if (pagingQuerySearchBlogPostVO.getStatus() != null && pagingQuerySearchBlogPostVO.getStatus() != "") {
                byte[] decodeStatus = Base64.getDecoder().decode(pagingQuerySearchBlogPostVO.getStatus());
                byte[] statusByte = rsaEncodeUtil.encodeRsaMessage(decodeStatus);
                pagingQuerySearchBlogPostVO.setStatus(new String(statusByte, "UTF-8"));
            }
            log.info("chenyun-blog:ArticleController:pagingQuerySearchArticleList:pageInfoVO:【{}】", pagingQuerySearchBlogPostVO);
            List<BlogPostVO> blogPostVOList = blogPostServiceDomain.pagingQuerySearchArticleList(pagingQuerySearchBlogPostVO);
            Long count = blogPostServiceDomain.searchBlogPostCount(pagingQuerySearchBlogPostVO.getTitle(), pagingQuerySearchBlogPostVO.getStatus());
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
            pagingQueryBlogPostVO.setCountOfPage(count);
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:pagingQuerySearchArticleList:Exception: 【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 下架文章
     */
    @DeleteMapping("/articleRemoval/{key}")
    public Result<Boolean> articleRemoval(@PathVariable("key") Long blogPostId) {
        try {
            Preconditions.checkNotNull(blogPostId, ExceptionLanguageConstant.BLOG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:ArticleController:articleRemoval:blogPostId:【{}】", blogPostId);
            return Result.ok(blogPostServiceDomain.articleRemoval(blogPostId));
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:articleRemoval:Exception: 【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 获取文章详细信息
     */
    @PostMapping("/getBlogInfo")
    public Result<BlogPostVO> getBlogInfo(@RequestBody BlogPostVO blogPostVO) {
        try {
            Preconditions.checkNotNull(blogPostVO.getKey(), ExceptionLanguageConstant.BLOG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:ArticleController:getBlogInfo:blogPostId:【{}】", blogPostVO.getKey());
            return Result.ok(blogPostServiceDomain.getBlogInfo(Long.valueOf(blogPostVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:getBlogInfo:Exception:【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 文章审核不通过
     */
    @PutMapping("/blogInfoRefuse")
    public Result<Boolean> blogInfoRefuse(@RequestBody BlogPostVO blogPostVO) {
        try {
            Preconditions.checkNotNull(blogPostVO.getKey(), ExceptionLanguageConstant.BLOG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:ArticleController:blogInfoRefuse:blogPostId:【{}】", blogPostVO.getKey());
            return Result.ok(blogPostServiceDomain.blogInfoRefuse(Long.valueOf(blogPostVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:blogInfoRefuse:Exception: 【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 文章审核通过
     */
    @PutMapping("/blogInfoProcess")
    public Result<Boolean> blogInfoProcess(@RequestBody BlogPostVO blogPostVO) {
        try {
            Preconditions.checkNotNull(blogPostVO.getKey(), ExceptionLanguageConstant.BLOG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:ArticleController:blogInfoProcess:blogPostId:【{}】", blogPostVO.getKey());
            return Result.ok(blogPostServiceDomain.blogInfoProcess(Long.valueOf(blogPostVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:blogInfoProcess:Exception: 【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 获取待审核的文章总数
     */
    @GetMapping("/reviewedBlogPostCount")
    public Result<Long> reviewedBlogPostCount() {
        try {
            return Result.ok(blogPostServiceDomain.reviewedBlogPostCount());
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:reviewedBlogPostCount:Exception: 【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }
}
