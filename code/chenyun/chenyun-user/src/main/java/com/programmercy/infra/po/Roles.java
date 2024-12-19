package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色表(Roles)实体类
 *
 * @author makejava
 * @since 2024-12-17 09:25:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {
    private static final long serialVersionUID = -61641898721194538L;
    /**
     * 主键，自增
     */
    private Integer id;
    /**
     * 角色名称（“游客”）
     */
    private String name;
    /**
     * 备注
     */
    private String remark;

}

