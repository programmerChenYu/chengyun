package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限信息表(Permissions)实体类
 *
 * @author makejava
 * @since 2024-12-17 09:27:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permissions implements Serializable {
    private static final long serialVersionUID = -66139882428953030L;
    /**
     * 主键，自增
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限标示（browse-blog-posts）
     */
    private String code;
    /**
     * 权限描述
     */
    private String remark;

}

