package com.programmercy.domain.service.impl;

import com.programmercy.api.feign.user.UserProfileService;
import com.programmercy.constant.RedisPrefixConstant;
import com.programmercy.domain.service.TagServiceDomain;
import com.programmercy.dto.UserDTO;
import com.programmercy.infra.po.Tag;
import com.programmercy.infra.service.BlogPostTagService;
import com.programmercy.infra.service.TagService;
import com.programmercy.util.RedisUtil;
import com.programmercy.vo.PagingQuerySearchTagListVO;
import com.programmercy.vo.TagVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Description: 实现类
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:23
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
public class TagDomainServiceImpl implements TagServiceDomain {

    @Resource
    private TagService tagService;

    @Resource
    private BlogPostTagService blogPostTagService;

    @Resource
    private UserProfileService userProfileService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<TagVO> hotTagRank() {
        // 定义优先级队列，让队列根据使用频率进行排序，由高到低
        PriorityQueue<TagVO> priorityQueue = new PriorityQueue<>((t1, t2) -> {
            return Integer.compare(t2.getFrequencyOfUse(), t1.getFrequencyOfUse());
        });
        // 获取所有的标签
        List<Tag> tagList = tagService.queryAllTag();
        // 获取所有标签在博文中出现的次数
        Long total = blogPostTagService.queryAllCount();
        // 遍历每一个标签，根据标签id，去博客标签关联表中进行计数，看被多少博文引用，并计算使用频率
        for (Tag tag : tagList) {
            Long count = blogPostTagService.countByTagId(tag.getTagId());
            // 将po转化为vo，并将使用频率加入到vo
            TagVO tagVO = new TagVO();
            tagVO = mapPO2VO(tag);
            tagVO.setFrequencyOfUse(Integer.valueOf((int)Math.round(((double)count/total)*100)));
            // 将vo加入优先级队列
            priorityQueue.add(tagVO);
        }
        // 依次将队列中的元素出队，加入到返回结果中
        List<TagVO> res = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            res.add(priorityQueue.remove());
        }
        return res;
    }

    @Override
    public Long searchTagCount(String tagName, String tagStatus) {
        return tagService.searchTagCount(tagName, tagStatus);
    }

    @Override
    public List<TagVO> pagingQuerySearchTagList(PagingQuerySearchTagListVO pagingQuerySearchTagListVO) {
        // 1. 搜索的标签名称
        String tagName = pagingQuerySearchTagListVO.getTagName();
        // 2. 搜索的标签状态
        String tagStatus = pagingQuerySearchTagListVO.getTagStatus();
        // 3. 当前页码
        Long currentPage = pagingQuerySearchTagListVO.getCurrentPage();
        // 4. 页面大小
        Long pageSize = pagingQuerySearchTagListVO.getPageSize();

        List<TagVO> res = new ArrayList<>();
        // 5. 既有标签名称又有标签状态
        if (tagName != null && tagName != "" && tagStatus != null && tagStatus != "") {
            List<Tag> tagList = tagService.pagingQueryTagListByLimit(tagName, tagStatus, currentPage, pageSize);
            res = mapPOList2VOList(tagList);
        } else if (tagName != null && tagName != "") {
            // 6. 只有标签名
            List<Tag> tagList = tagService.pagingQueryTagListByLimit(tagName, null, currentPage, pageSize);
            res = mapPOList2VOList(tagList);
        } else if (tagStatus != null && tagStatus != "") {
            // 7. 只有标签状态
            List<Tag> tagList = tagService.pagingQueryTagListByLimit(null, tagStatus, currentPage, pageSize);
            res = mapPOList2VOList(tagList);
        }
        return res;
    }

    @Override
    public Boolean batchEnable(List<Long> tagIds) {
        Long res = tagService.batchEnable(tagIds);
        return res == tagIds.size();
    }

    @Override
    public Boolean batchDeactivate(List<Long> tagIds) {
        Long res = tagService.batchDeactivate(tagIds);
        return res == tagIds.size();
    }

    @Override
    public Boolean createTag(String tagName, Integer tagStatus) {
        try {
            Tag tag = new Tag();
            // 设置标签名
            tag.setTagName(tagName);
            // 设置标签状态
            tag.setTagStatus(tagStatus);
            // 设置标签创建时间
            ZonedDateTime now = ZonedDateTime.now();
            StringBuilder sb = new StringBuilder();
            sb.append(now.getYear()).append("/").append(now.getMonth().getValue()).append("/").append(now.getDayOfMonth());
            tag.setCreateTime(sb.toString());
            // 设置标签创建者
            // TODO 要根据 token 来获取是谁创建的
            tag.setCreatorId(1L);
            // 设置唯一标识符
            Long tagId = redisUtil.SignalSender(RedisPrefixConstant.TAG_SEQUENCE);
            tag.setTagId(tagId);
            Tag insert = tagService.insert(tag);
            return insert.getTagName().equals(tagName);
        } catch (Exception e) {
            redisUtil.stringDecrement(RedisPrefixConstant.TAG_SEQUENCE);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Boolean deleteTag(Long tagId) {
        return tagService.deleteById(tagId);
    }

    @Override
    public Boolean enableTag(Long tagId) {
        Tag tag = new Tag();
        tag.setTagId(tagId);
        tag.setTagStatus(1);
        Tag update = tagService.update(tag);
        return update.getTagStatus() == 1;
    }

    @Override
    public Boolean deactivateTag(Long tagId) {
        Tag tag = new Tag();
        tag.setTagId(tagId);
        tag.setTagStatus(0);
        Tag update = tagService.update(tag);
        return update.getTagStatus() == 0;
    }

    @Override
    public Long tagCount() {
        return tagService.tagCount();
    }

    @Override
    public List<TagVO> pagingQueryTagList(Long currentPage, Long pageSize) {
        List<Tag> tagList = tagService.pagingQueryTagList(currentPage, pageSize);
        List<TagVO> tagVOList = mapPOList2VOList(tagList);
        return tagVOList;
    }

    /**
     * 将 PO 转换为 VO
     * @param tag
     * @return
     */
    private TagVO mapPO2VO(Tag tag) {
        TagVO tagVO = new TagVO();
        BeanUtils.copyProperties(tag, tagVO);
        // 设置 key，对应 id
        tagVO.setKey(tag.getTagId().toString());
        // 根据创建者id，获取创建者昵称，远程调用
        UserDTO userDTO = new UserDTO();
        userDTO.setKey(tag.getCreatorId().toString());
        UserDTO userInfo = userProfileService.getUserInfoByIdAPI(userDTO);
        tagVO.setCreator(userInfo.getNickname());
        return tagVO;
    }

    /**
     * 将 POList 转换为 VOList
     * @param tagList
     * @return
     */
    private List<TagVO> mapPOList2VOList(List<Tag> tagList) {
        ArrayList<TagVO> tagVOS = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVOS.add(mapPO2VO(tag));
        }
        return tagVOS;
    }
}
