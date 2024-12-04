package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 博客标签关联表(BlogPostTag)实体类
 *
 * @author makejava
 * @since 2024-12-03 16:01:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostTag implements Serializable {
    private static final long serialVersionUID = -59541733837457002L;
    /**
     * 博客唯一标识符
     */
    private Long blogPostId;
    /**
     * 标签唯一标识符
     */
    private Long tagId;

}

