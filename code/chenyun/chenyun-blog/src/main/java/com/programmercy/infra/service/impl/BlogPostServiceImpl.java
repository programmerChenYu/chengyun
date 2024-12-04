package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.BlogPost;
import com.programmercy.infra.mapper.BlogPostDao;
import com.programmercy.infra.service.BlogPostService;
import com.programmercy.vo.PageInfoVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客表(BlogPost)表服务实现类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-02 10:14:10
 */
@Service("blogPostService")
public class BlogPostServiceImpl implements BlogPostService {

    @Resource
    private BlogPostDao blogPostDao;

    /**
     * 通过ID查询单条数据
     *
     * @param blogPostId 主键
     * @return 实例对象
     */
    @Override
    public BlogPost queryById(Long blogPostId) {
        return this.blogPostDao.queryById(blogPostId);
    }

    /**
     * 新增数据
     *
     * @param blogPost 实例对象
     * @return 实例对象
     */
    @Override
    public BlogPost insert(BlogPost blogPost) {
        this.blogPostDao.insert(blogPost);
        return blogPost;
    }

    /**
     * 修改数据
     *
     * @param blogPost 实例对象
     * @return 实例对象
     */
    @Override
    public BlogPost update(BlogPost blogPost) {
        this.blogPostDao.update(blogPost);
        return this.queryById(blogPost.getBlogPostId());
    }

    /**
     * 通过主键删除数据
     *
     * @param blogPostId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long blogPostId) {
        return this.blogPostDao.deleteById(blogPostId) > 0;
    }

    @Override
    public List<BlogPost> pagingQueryArticleList(PageInfoVO pageInfoVO) {
        BlogPost blogPost = new BlogPost();
        return this.blogPostDao.queryAllByLimit(blogPost, (pageInfoVO.getCurrentPage()-1)* pageInfoVO.getPageSize(), pageInfoVO.getPageSize());
    }

    @Override
    public Long blogPostCount() {
        return this.blogPostDao.count(new BlogPost());
    }

    @Override
    public Long auditedBlogPostCount() {
        BlogPost blogPost = new BlogPost();
        blogPost.setStatus(2);
        return this.blogPostDao.count(blogPost);
    }

    @Override
    public List<BlogPost> pagingQueryAuditedArticleList(PageInfoVO pageInfoVO) {
        BlogPost blogPost = new BlogPost();
        blogPost.setStatus(2);
        return this.blogPostDao.queryAllByLimit(blogPost, (pageInfoVO.getCurrentPage()-1)* pageInfoVO.getPageSize(), pageInfoVO.getPageSize());
    }

    @Override
    public List<BlogPost> pagingQueryReviewedArticleList(PageInfoVO pageInfoVO) {
        BlogPost blogPost = new BlogPost();
        blogPost.setStatus(1);
        return this.blogPostDao.queryAllByLimit(blogPost, (pageInfoVO.getCurrentPage()-1)* pageInfoVO.getPageSize(), pageInfoVO.getPageSize());
    }

    @Override
    public Long reviewedBlogPostCount() {
        BlogPost blogPost = new BlogPost();
        blogPost.setStatus(1);
        return this.blogPostDao.count(blogPost);
    }

    @Override
    public List<BlogPost> pagingQueryArticleListByLimit(String title, String status, Long currentPage, Long pageSize) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        if (status != null && status != "") {
            blogPost.setStatus(Integer.valueOf(status));
        }
        return this.blogPostDao.pagingQueryArticleListByLimit(blogPost, (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public Long searchBlogPostCount(String title, String status) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        if (status != null && status != "") {
            blogPost.setStatus(Integer.valueOf(status));
        }
        return this.blogPostDao.searchBlogPostCount(blogPost);
    }
}
