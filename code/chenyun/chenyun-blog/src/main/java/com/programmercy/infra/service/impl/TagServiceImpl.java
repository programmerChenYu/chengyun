package com.programmercy.infra.service.impl;

import com.programmercy.infra.po.Tag;
import com.programmercy.infra.mapper.TagDao;
import com.programmercy.infra.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 标签表(Tag)表服务实现类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-29 09:00:57
 */
@Service("tagService")
public class TagServiceImpl implements TagService {

    @Resource
    private TagDao tagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    @Override
    public Tag queryById(Long tagId) {
        return this.tagDao.queryById(tagId);
    }

    /**
     * 新增数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    @Override
    public Tag insert(Tag tag) {
        this.tagDao.insert(tag);
        return tag;
    }

    /**
     * 修改数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    @Override
    public Tag update(Tag tag) {
        this.tagDao.update(tag);
        return this.queryById(tag.getTagId());
    }

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long tagId) {
        return this.tagDao.deleteById(tagId) > 0;
    }

    @Override
    public List<Tag> pagingQueryTagList(Long currentPage, Long pageSize) {
        return this.tagDao.queryAllByLimit(new Tag(), (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public Long tagCount() {
        return this.tagDao.count(new Tag());
    }

    @Override
    public Long batchDeactivate(List<Long> tagIds) {
        return this.tagDao.batchDeactivate(tagIds);
    }

    @Override
    public Long batchEnable(List<Long> tagIds) {
        return this.tagDao.batchEnable(tagIds);
    }

    @Override
    public List<Tag> pagingQueryTagListByLimit(String tagName, String tagStatus, Long currentPage, Long pageSize) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        if (tagStatus != null && tagStatus != "") {
            tag.setTagStatus(Integer.valueOf(tagStatus));
        }
        return this.tagDao.querySearchByLimit(tag, (currentPage-1)*pageSize, pageSize);
    }

    @Override
    public Long searchTagCount(String tagName, String tagStatus) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        if (tagStatus != null && tagStatus != "") {
            tag.setTagStatus(Integer.valueOf(tagStatus));
        }
        return this.tagDao.searchCount(tag);
    }

    @Override
    public List<Tag> queryAllTag() {
        return this.tagDao.queryAllTag();
    }
}
