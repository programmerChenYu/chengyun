package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 博客表(BlogPost)实体类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-02 10:14:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost implements Serializable {
    private static final long serialVersionUID = 249650548284774003L;
    /**
     * 博客唯一标识符
     */
    private Long blogPostId;
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
    private Long authorId;
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

