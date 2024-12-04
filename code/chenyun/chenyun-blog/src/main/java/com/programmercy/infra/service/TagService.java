package com.programmercy.infra.service;

import com.programmercy.infra.po.Tag;

import java.util.List;

/**
 * 标签表(Tag)表服务接口
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-29 09:00:57
 */
public interface TagService {

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    Tag queryById(Long tagId);

    /**
     * 新增数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    Tag insert(Tag tag);

    /**
     * 修改数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    Tag update(Tag tag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 是否成功
     */
    boolean deleteById(Long tagId);

    /**
     * 获取分页的标签列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Tag> pagingQueryTagList(Long currentPage, Long pageSize);

    /**
     * 获取标签总数
     * @return
     */
    Long tagCount();

    /**
     * 批量停用标签
     * @param tagIds
     * @return
     */
    Long batchDeactivate(List<Long> tagIds);

    /**
     * 批量启用标签
     * @param tagIds
     * @return
     */
    Long batchEnable(List<Long> tagIds);

    /**
     * 根据标签名和标签状态来查询
     * @param tagName
     * @param tagStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Tag> pagingQueryTagListByLimit(String tagName, String tagStatus, Long currentPage, Long pageSize);

    /**
     * 获取搜索的标签总数
     * @param tagName
     * @param tagStatus
     * @return
     */
    Long searchTagCount(String tagName, String tagStatus);

    /**
     * 获取所有的标签
     * @return
     */
    List<Tag> queryAllTag();
}
