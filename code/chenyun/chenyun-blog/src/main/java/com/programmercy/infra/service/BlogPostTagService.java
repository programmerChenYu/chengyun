package com.programmercy.infra.service;

import com.programmercy.infra.po.BlogPostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 博客标签关联表(BlogPostTag)表服务接口
 *
 * @author makejava
 * @since 2024-12-03 16:01:33
 */
public interface BlogPostTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param blogPostId 主键
     * @return 实例对象
     */
    BlogPostTag queryById(Long blogPostId);

    /**
     * 新增数据
     *
     * @param blogPostTag 实例对象
     * @return 实例对象
     */
    BlogPostTag insert(BlogPostTag blogPostTag);

    /**
     * 修改数据
     *
     * @param blogPostTag 实例对象
     * @return 实例对象
     */
    BlogPostTag update(BlogPostTag blogPostTag);

    /**
     * 通过主键删除数据
     *
     * @param blogPostId 主键
     * @return 是否成功
     */
    boolean deleteById(Long blogPostId);

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
    Long countByTagId(Long tagId);
}
