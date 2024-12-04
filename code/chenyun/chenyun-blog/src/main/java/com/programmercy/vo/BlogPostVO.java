package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 博文VO
 * Created by 爱吃小鱼的橙子 on 2024-12-02 10:22
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostVO {

    /**
     * 博客唯一标识符
     */
    private String key;
    /**
     * 标题
     */
    private String title;
    /**
     * 正文内容
     */
    private String content;
    /**
     * 作者id
     */
    private String author;
    /**
     * 0：草稿；1：待审核；2：公开
     */
    private Integer status;
    /**
     * 浏览次数
     */
    private Long views;
    /**
     * 点赞次数
     */
    private Long likes;
    /**
     * 收藏次数（通过关联的 bookmark 计算）
     */
    private Long bookmarksCount;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 更新时间
     */
    private String updatedAt;

}
