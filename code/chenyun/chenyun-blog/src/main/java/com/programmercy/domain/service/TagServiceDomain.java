package com.programmercy.domain.service;

import com.programmercy.vo.PagingQuerySearchTagListVO;
import com.programmercy.vo.TagVO;

import java.util.List;

/**
 * Description: 标签服务类，领域层
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:22
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface TagServiceDomain {

    /**
     * 获取分页的标签列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TagVO> pagingQueryTagList(Long currentPage, Long pageSize);

    /**
     * 获取标签总数
     * @return
     */
    Long tagCount();

    /**
     * 禁用标签
     * @param tagId
     * @return
     */
    Boolean deactivateTag(Long tagId);

    /**
     * 启用标签
     * @param tagId
     * @return
     */
    Boolean enableTag(Long tagId);

    /**
     * 删除标签
     * @param tagId
     * @return
     */
    Boolean deleteTag(Long tagId);

    /**
     * 创建标签
     * @param tagName
     * @param tagStatus
     * @return
     */
    Boolean createTag(String tagName, Integer tagStatus);

    /**
     * 批量停用标签
     * @param tagIds
     * @return
     */
    Boolean batchDeactivate(List<Long> tagIds);

    /**
     * 批量启用标签
     * @param tagIds
     * @return
     */
    Boolean batchEnable(List<Long> tagIds);

    /**
     * 获取分页的搜索标签列表
     * @param pagingQuerySearchTagListVO
     * @return
     */
    List<TagVO> pagingQuerySearchTagList(PagingQuerySearchTagListVO pagingQuerySearchTagListVO);

    /**
     * 获取搜索的标签总数
     * @param tagName
     * @param tagStatus
     * @return
     */
    Long searchTagCount(String tagName, String tagStatus);

    /**
     * 获取标签的使用排行
     * @return
     */
    List<TagVO> hotTagRank();
}
