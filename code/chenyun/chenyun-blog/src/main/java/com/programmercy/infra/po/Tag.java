package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 标签表(Tag)实体类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-29 09:00:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = -18948847294323014L;
    /**
     * 标签唯一标识符
     */
    private Long tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签状态（0：停用；1：启用）
     */
    private Integer tagStatus;
    /**
     * 标签创建时间
     */
    private String createTime;
    /**
     * 标签创建人
     */
    private Long creatorId;

}

