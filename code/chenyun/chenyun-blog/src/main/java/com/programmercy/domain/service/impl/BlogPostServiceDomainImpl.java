package com.programmercy.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.programmercy.api.feign.user.UserProfileService;
import com.programmercy.domain.service.BlogPostServiceDomain;
import com.programmercy.dto.UserDTO;
import com.programmercy.infra.po.BlogPost;
import com.programmercy.infra.service.BlogPostService;
import com.programmercy.vo.BlogPostVO;
import com.programmercy.vo.PageInfoVO;
import com.programmercy.vo.PagingQuerySearchBlogPostVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 博文服务类的实现类
 * Created by 爱吃小鱼的橙子 on 2024-12-02 10:19
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
@Slf4j
public class BlogPostServiceDomainImpl implements BlogPostServiceDomain {

    @Resource
    private BlogPostService blogPostService;

    @Resource
    private UserProfileService userProfileService;


    @Override
    public Boolean blogInfoProcess(Long blogPostId) {
        BlogPost blogPost = new BlogPost();
        blogPost.setBlogPostId(blogPostId);
        blogPost.setStatus(2);
        BlogPost update = blogPostService.update(blogPost);
        // TODO 对用户发送提醒，审核未通过
        return update.getStatus() == 2;
    }

    @Override
    public Boolean blogInfoRefuse(Long blogPostId) {
        BlogPost blogPost = new BlogPost();
        blogPost.setBlogPostId(blogPostId);
        blogPost.setStatus(0);
        BlogPost update = blogPostService.update(blogPost);
        // TODO 对用户发送提醒，审核未通过
        return update.getStatus() == 0;
    }

    @Override
    public BlogPostVO getBlogInfo(Long blogPostId) {
        BlogPost blogPost = blogPostService.queryById(blogPostId);
        if (log.isInfoEnabled()) {
            log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:getBlogInfo:blogPost: [{}]", JSON.toJSONString(blogPost));
        }
        return mapPO2VO(blogPost);
    }

    @Override
    public Boolean articleRemoval(Long blogPostId) {
        return blogPostService.deleteById(blogPostId);
    }

    @Override
    public Long searchBlogPostCount(String title, String status) {
        return blogPostService.searchBlogPostCount(title, status);
    }

    @Override
    public List<BlogPostVO> pagingQuerySearchArticleList(PagingQuerySearchBlogPostVO pagingQuerySearchBlogPostVO) {
        String title = pagingQuerySearchBlogPostVO.getTitle();
        String status = pagingQuerySearchBlogPostVO.getStatus();
        Long currentPage = pagingQuerySearchBlogPostVO.getCurrentPage();
        Long pageSize = pagingQuerySearchBlogPostVO.getPageSize();

        List<BlogPostVO> blogPostVOS = new ArrayList<>();
        if (title != null && title != "" && status != null && status != "") {
            List<BlogPost> blogPostList = blogPostService.pagingQueryArticleListByLimit(title, status, currentPage, pageSize);
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:pagingQuerySearchArticleList:blogPostList: [{}]", JSON.toJSONString(blogPostList));
            }
            blogPostVOS = mapPOList2VOList(blogPostList);
        } else if (title != null || title != "") {
            List<BlogPost> blogPostList = blogPostService.pagingQueryArticleListByLimit(title, null, currentPage, pageSize);
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:pagingQuerySearchArticleList:blogPostList: [{}]", JSON.toJSONString(blogPostList));
            }
            blogPostVOS = mapPOList2VOList(blogPostList);
        } else if (status != null || status != "") {
            List<BlogPost> blogPostList = blogPostService.pagingQueryArticleListByLimit(null, status, currentPage, pageSize);
            if (log.isInfoEnabled()) {
                log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:pagingQuerySearchArticleList:blogPostList: [{}]", JSON.toJSONString(blogPostList));
            }
            blogPostVOS = mapPOList2VOList(blogPostList);
        }
        return blogPostVOS;
    }

    @Override
    public Long reviewedBlogPostCount() {
        return blogPostService.reviewedBlogPostCount();
    }

    @Override
    public List<BlogPostVO> pagingQueryReviewedArticleList(PageInfoVO pageInfoVO) {
        List<BlogPost> blogPostList = blogPostService.pagingQueryReviewedArticleList(pageInfoVO);
        if (log.isInfoEnabled()) {
            log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:pagingQueryReviewedArticleList:blogPostList: [{}]", JSON.toJSONString(blogPostList));
        }
        List<BlogPostVO> blogPostVOList = mapPOList2VOList(blogPostList);
        return blogPostVOList;
    }

    @Override
    public Long auditedBlogPostCount() {
        return blogPostService.auditedBlogPostCount();
    }

    @Override
    public List<BlogPostVO> pagingQueryAuditedArticleList(PageInfoVO pageInfoVO) {
        List<BlogPost> blogPostList = blogPostService.pagingQueryAuditedArticleList(pageInfoVO);
        if (log.isInfoEnabled()) {
            log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:pagingQueryAuditedArticleList:blogPostList: [{}]", JSON.toJSONString(blogPostList));
        }
        List<BlogPostVO> blogPostVOList = mapPOList2VOList(blogPostList);
        return blogPostVOList;
    }

    @Override
    public Long blogPostCount() {
        return blogPostService.blogPostCount();
    }

    @Override
    public List<BlogPostVO> pagingQueryArticleList(PageInfoVO pageInfoVO) {
        List<BlogPost> blogPostList = blogPostService.pagingQueryArticleList(pageInfoVO);
        if (log.isInfoEnabled()) {
            log.info("chenyun-blog:domain:service:impl:BlogPostServiceDomainImpl:pagingQueryArticleList:blogPostList: [{}]", JSON.toJSONString(blogPostList));
        }
        List<BlogPostVO> blogPostVOList = mapPOList2VOList(blogPostList);
        return blogPostVOList;
    }

    private BlogPostVO mapPO2VO(BlogPost blogPost) {
        BlogPostVO blogPostVO = new BlogPostVO();
        BeanUtils.copyProperties(blogPost, blogPostVO);
        blogPostVO.setKey(blogPost.getBlogPostId().toString());
        // 作者
        UserDTO userDTO = new UserDTO();
        userDTO.setKey(blogPost.getAuthorId().toString());
        UserDTO userInfo = userProfileService.getUserInfoByIdAPI(userDTO);
        blogPostVO.setAuthor(userInfo.getNickname());
        return blogPostVO;
    }

    private List<BlogPostVO> mapPOList2VOList(List<BlogPost> blogPostList) {
        ArrayList<BlogPostVO> blogPostVOS = new ArrayList<>();
        for (BlogPost blogPost : blogPostList) {
            blogPostVOS.add(mapPO2VO(blogPost));
        }
        return blogPostVOS;
    }
}
