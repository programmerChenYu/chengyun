package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.BlogPostTag;
import com.programmercy.infra.mapper.BlogPostTagDao;
import com.programmercy.infra.service.BlogPostTagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 博客标签关联表(BlogPostTag)表服务实现类
 *
 * @author makejava
 * @since 2024-12-03 16:01:33
 */
@Service("blogPostTagService")
public class BlogPostTagServiceImpl implements BlogPostTagService {

    @Resource
    private BlogPostTagDao blogPostTagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param blogPostId 主键
     * @return 实例对象
     */
    @Override
    public BlogPostTag queryById(Long blogPostId) {
        return this.blogPostTagDao.queryById(blogPostId);
    }

    /**
     * 新增数据
     *
     * @param blogPostTag 实例对象
     * @return 实例对象
     */
    @Override
    public BlogPostTag insert(BlogPostTag blogPostTag) {
        this.blogPostTagDao.insert(blogPostTag);
        return blogPostTag;
    }

    /**
     * 修改数据
     *
     * @param blogPostTag 实例对象
     * @return 实例对象
     */
    @Override
    public BlogPostTag update(BlogPostTag blogPostTag) {
        this.blogPostTagDao.update(blogPostTag);
        return this.queryById(blogPostTag.getBlogPostId());
    }

    /**
     * 通过主键删除数据
     *
     * @param blogPostId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long blogPostId) {
        return this.blogPostTagDao.deleteById(blogPostId) > 0;
    }

    @Override
    public Long queryAllCount() {
        return this.blogPostTagDao.queryAllCount();
    }

    @Override
    public Long countByTagId(Long tagId) {
        return this.blogPostTagDao.countByTagId(tagId);
    }
}
