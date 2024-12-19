package com.programmercy.controller;

import com.alibaba.fastjson.JSON;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Description: 博文控制类
 * Created by 爱吃小鱼的橙子 on 2024-12-02 10:05
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@RequestMapping("/article")
@Slf4j
@SuppressWarnings("unchecked")
public class ArticleController {

    @Resource
    private BlogPostServiceDomain blogPostServiceDomain;
    @Resource
    private RsaEncodeUtil rsaEncodeUtil;
    @Resource
    private ExecutorService blogPostThreadPool;

    /**
     * 分页查询博文列表
     */
    @PostMapping("/pagingQueryArticleList")
    public Result<List<PagingQueryBlogPostVO>> pagingQueryArticleList(@RequestBody PageInfoVO pageInfoVO) {
        try {
            Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:pagingQueryArticleList:pageInfoVO: [{}]", JSON.toJSONString(pageInfoVO));
            }
            CompletableFuture<List<BlogPostVO>> blogPostVOListFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.pagingQueryArticleList(pageInfoVO);
            }, blogPostThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.blogPostCount();
            }, blogPostThreadPool);
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            blogPostVOListFuture.thenAcceptBoth(countFuture, (blogPostVOList, count) -> {
                pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
                pagingQueryBlogPostVO.setCountOfPage(count);
            }).get();
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:pagingQueryArticleList:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:pagingQueryAuditedArticleList:pageInfoVO: [{}]", JSON.toJSONString(pageInfoVO));
            }
            CompletableFuture<List<BlogPostVO>> blogPostVOListFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.pagingQueryAuditedArticleList(pageInfoVO);
            }, blogPostThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.auditedBlogPostCount();
            }, blogPostThreadPool);
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            blogPostVOListFuture.thenAcceptBoth(countFuture, (blogPostVOList, count) -> {
                pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
                pagingQueryBlogPostVO.setCountOfPage(count);
            }).get();
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:ArticleController:pagingQueryAuditedArticleList:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:pagingQueryReviewedArticleList:pageInfoVO: [{}]", JSON.toJSONString(pageInfoVO));
            }
            CompletableFuture<List<BlogPostVO>> blogPostVOListFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.pagingQueryReviewedArticleList(pageInfoVO);
            }, blogPostThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.reviewedBlogPostCount();
            }, blogPostThreadPool);
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            blogPostVOListFuture.thenAcceptBoth(countFuture, (blogPostVOList, count) -> {
                pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
                pagingQueryBlogPostVO.setCountOfPage(count);
            }).get();
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:controller:ArticleController:pagingQueryReviewedArticleList:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:pagingQuerySearchArticleList:pagingQuerySearchBlogPostVO: [{}]", JSON.toJSONString(pagingQuerySearchBlogPostVO));
            }
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
            CompletableFuture<List<BlogPostVO>> blogPostVOListFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.pagingQuerySearchArticleList(pagingQuerySearchBlogPostVO);
            }, blogPostThreadPool);
            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                return blogPostServiceDomain.searchBlogPostCount(pagingQuerySearchBlogPostVO.getTitle(), pagingQuerySearchBlogPostVO.getStatus());
            }, blogPostThreadPool);
            PagingQueryBlogPostVO pagingQueryBlogPostVO = new PagingQueryBlogPostVO();
            blogPostVOListFuture.thenAcceptBoth(countFuture, (blogPostVOList, count) -> {
                pagingQueryBlogPostVO.setBlogPostList(blogPostVOList);
                pagingQueryBlogPostVO.setCountOfPage(count);
            }).get();
            return Result.ok(pagingQueryBlogPostVO);
        } catch (Exception e) {
            log.error("chenyun-blog:controller:ArticleController:pagingQuerySearchArticleList:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:articleRemoval:blogPostId: [{}]", JSON.toJSONString(blogPostId));
            }
            return Result.ok(blogPostServiceDomain.articleRemoval(blogPostId));
        } catch (Exception e) {
            log.error("chenyun-blog:controller:ArticleController:articleRemoval:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:getBlogInfo:blogPostVO: [{}]", JSON.toJSONString(blogPostVO));
            }
            return Result.ok(blogPostServiceDomain.getBlogInfo(Long.valueOf(blogPostVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-blog:controller:ArticleController:getBlogInfo:Exception [{},{}]", e.getMessage(), e.getStackTrace());
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
            Preconditions.checkNotNull(blogPostVO.getKey(), ExceptionLanguageConstant.BLOG_ID_NULL_EXCEPTION);
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:blogInfoRefuse:blogPostVO: [{}]", JSON.toJSONString(blogPostVO));
            }
            return Result.ok(blogPostServiceDomain.blogInfoRefuse(Long.valueOf(blogPostVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-blog:controller:ArticleController:blogInfoRefuse:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:controller:ArticleController:blogInfoProcess:blogPostVO: [{}]", JSON.toJSONString(blogPostVO));
            }
            return Result.ok(blogPostServiceDomain.blogInfoProcess(Long.valueOf(blogPostVO.getKey())));
        } catch (Exception e) {
            log.error("chenyun-blog:controller:ArticleController:blogInfoProcess:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
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
            log.error("chenyun-blog:controller:ArticleController:reviewedBlogPostCount:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }
}
