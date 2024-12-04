package com.programmercy.controller;

import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.TagServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.util.RsaEncodeUtil;
import com.programmercy.vo.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 标签相关的控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:04
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@RequestMapping("/tag")
@Slf4j
public class TagController {

    @Resource
    private TagServiceDomain tagServiceDomain;

    @Resource
    private RsaEncodeUtil rsaEncodeUtil;

    /**
     * 分页查询标签列表
     */
    @PostMapping("/pagingQueryTagList")
    public Result<PagingQueryTagListVO> pagingQueryTagList(@RequestBody PageInfoVO pageInfoVO) {
        try {
            Preconditions.checkArgument(pageInfoVO.getPageSize() > 0, ExceptionLanguageConstant.PAGE_SIZE_EXCEPTION);
            Preconditions.checkArgument(pageInfoVO.getCurrentPage() > 0, ExceptionLanguageConstant.CURRENT_PAGE_EXCEPTION);
            log.info("chenyun-blog:TagController:pagingQueryTagList:VO, {}", pageInfoVO);
            // 获取分页的标签列表
            List<TagVO> tagVOList = tagServiceDomain.pagingQueryTagList(pageInfoVO.getCurrentPage(), pageInfoVO.getPageSize());
            // 获取标签总数
            Long count = tagServiceDomain.tagCount();
            PagingQueryTagListVO queryTagListVO = new PagingQueryTagListVO();
            queryTagListVO.setTagList(tagVOList);
            queryTagListVO.setCountOfPage(count);
            return Result.ok(queryTagListVO);
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:pagingQueryTagList:Exception, {}", e.getMessage());
            return Result.fail(null);
        }
    }

    /**
     * 禁用标签
     */
    @PutMapping("/deactivateTag")
    public Result<Boolean> deactivateTag(@RequestBody TagVO tagVO) {
        try {
            Preconditions.checkNotNull(tagVO.getKey(), ExceptionLanguageConstant.TAG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:TagController:deactivateTag:tagId, {}", tagVO.getKey());
            Boolean res = tagServiceDomain.deactivateTag(Long.valueOf(tagVO.getKey()));
            return Result.ok(res);
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:deactivateTag:Exception, {}", e.getMessage());
            return Result.fail(false);
        }
    }

    /**
     * 启用标签
     */
    @PutMapping("/enableTag")
    public Result<Boolean> enableTag(@RequestBody TagVO tagVO) {
        try {
            Preconditions.checkNotNull(tagVO.getKey(), ExceptionLanguageConstant.TAG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:TagController:enableTag:tagId, {}", tagVO.getKey());
            Boolean res = tagServiceDomain.enableTag(Long.valueOf(tagVO.getKey()));
            return Result.ok(res);
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:enableTag:Exception, {}", e.getMessage());
            return Result.fail(false);
        }
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/deleteTag/{tagId}")
    public Result<Boolean> deleteTag(@PathVariable("tagId") String tagId) {
        try {
            Preconditions.checkNotNull(tagId, ExceptionLanguageConstant.TAG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:TagController:enableTag:tagId, {}", tagId);
            return Result.ok(tagServiceDomain.deleteTag(Long.valueOf(tagId)));
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:deleteTag:Exception, {}", e.getMessage());
            return Result.fail(false);
        }
    }

    /**
     * 新建标签
     */
    @PostMapping("/createTag")
    public Result<Boolean> createTag(@RequestBody TagVO tagVO) {
        try {
            Preconditions.checkArgument(tagVO.getTagName() != null, ExceptionLanguageConstant.TAG_NAME_NULL_EXCEPTION);
            Preconditions.checkArgument(tagVO.getTagName() != "", ExceptionLanguageConstant.TAG_NAME_NULL_EXCEPTION);
            log.info("chenyun-blog:TagController:createTag:tagName, {}", tagVO.getTagName());
            Integer tagStatus = tagVO.getTagStatus();
            if (tagStatus == null) {
                tagStatus = 0;
            }
            log.info("chenyun-blog:TagController:createTag:tagStatus, {}", tagStatus);
            return Result.ok(tagServiceDomain.createTag(tagVO.getTagName(), tagStatus));
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:createTag:Exception, {}", e.getMessage());
            return Result.fail(false);
        }
    }

    /**
     * 批量停用标签
     */
    @PutMapping("/batchDeactivate")
    public Result<Boolean> batchDeactivate(@RequestBody TagBatchOptionVO tagBatchOptionVO) {
        try {
            Preconditions.checkNotNull(tagBatchOptionVO.getTagIds(), ExceptionLanguageConstant.BATCH_TAG_ID_NULL_EXCEPTION);
            Preconditions.checkArgument(tagBatchOptionVO.getTagIds().size() > 0, ExceptionLanguageConstant.BATCH_TAG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:TagController:batchDeactivate:tagIds, {}", tagBatchOptionVO.getTagIds());
            List<Long> tagIds = tagBatchOptionVO.getTagIds().stream().map((tagId) -> {
                return Long.valueOf(tagId);
            }).collect(Collectors.toList());
            return Result.ok(tagServiceDomain.batchDeactivate(tagIds));
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:batchDeactivate:Exception, {}", e.getMessage());
            return Result.fail(false);
        }
    }

    /**
     * 批量启用
     */
    @PutMapping("/batchEnable")
    public Result<Boolean> batchEnable(@RequestBody TagBatchOptionVO tagBatchOptionVO) {
        try {
            Preconditions.checkNotNull(tagBatchOptionVO.getTagIds(), ExceptionLanguageConstant.BATCH_TAG_ID_NULL_EXCEPTION);
            Preconditions.checkArgument(tagBatchOptionVO.getTagIds().size() > 0, ExceptionLanguageConstant.BATCH_TAG_ID_NULL_EXCEPTION);
            log.info("chenyun-blog:TagController:batchEnable:tagIds, {}", tagBatchOptionVO.getTagIds());
            List<Long> tagIds = tagBatchOptionVO.getTagIds().stream().map((tagId) -> {
                return Long.valueOf(tagId);
            }).collect(Collectors.toList());
            return Result.ok(tagServiceDomain.batchEnable(tagIds));
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:batchEnable:Exception, {}", e.getMessage());
            return Result.fail(false);
        }
    }

    /**
     * 搜索标签
     */
    @PostMapping("/pagingQuerySearchTagList")
    public Result<PagingQueryTagListVO> pagingQuerySearchTagList(@RequestBody PagingQuerySearchTagListVO pagingQuerySearchTagListVO) {
        try {
            Preconditions.checkArgument((pagingQuerySearchTagListVO.getTagName() != null || pagingQuerySearchTagListVO.getTagName() != "")
                    && (pagingQuerySearchTagListVO.getTagStatus() != null || pagingQuerySearchTagListVO.getTagStatus() != ""),
                    ExceptionLanguageConstant.SEARCH_TAG_NAME_AND_STATUS_NULL_EXCEPTION);
            if (pagingQuerySearchTagListVO.getTagName() != null) {
                byte[] decodeName = Base64.getDecoder().decode(pagingQuerySearchTagListVO.getTagName());
                byte[] tagNameByte = rsaEncodeUtil.encodeRsaMessage(decodeName);
                pagingQuerySearchTagListVO.setTagName(new String(tagNameByte, "UTF-8"));
            }
            if (pagingQuerySearchTagListVO.getTagStatus() != null) {
                byte[] decodeStatus = Base64.getDecoder().decode(pagingQuerySearchTagListVO.getTagStatus());
                byte[] tagStatusByte = rsaEncodeUtil.encodeRsaMessage(decodeStatus);
                pagingQuerySearchTagListVO.setTagStatus(new String(tagStatusByte, "UTF-8"));
            }
            log.info("chenyun-blog:TagController:pagingQuerySearchTagList:pagingQuerySearchTagListVO, {}", pagingQuerySearchTagListVO);
            // 获取分页的搜索标签列表
            List<TagVO> tagVOList = tagServiceDomain.pagingQuerySearchTagList(pagingQuerySearchTagListVO);
            // 获取搜索的标签总数
            Long count = tagServiceDomain.searchTagCount(pagingQuerySearchTagListVO.getTagName(), pagingQuerySearchTagListVO.getTagStatus());
            PagingQueryTagListVO queryTagListVO = new PagingQueryTagListVO();
            queryTagListVO.setTagList(tagVOList);
            queryTagListVO.setCountOfPage(count);
            return Result.ok(queryTagListVO);
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:pagingQuerySearchTagList:Exception, {}", e.getMessage());
            return Result.fail(null);
        }
    }

    /**
     * 获取标签的使用排行
     */
    @GetMapping("/hotTagRank")
    public Result<List<TagVO>> hotTagRank() {
        try {
            return Result.ok(tagServiceDomain.hotTagRank());
        } catch (Exception e) {
            log.error("chenyun-blog:TagController:hotTagRank:Exception, {}", e.getStackTrace());
            return Result.fail(null);
        }
    }
}
