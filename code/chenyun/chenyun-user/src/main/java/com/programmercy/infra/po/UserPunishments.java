package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户惩罚记录表(UserPunishments)实体类
 *
 * @author makejava
 * @since 2024-12-17 09:48:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPunishments implements Serializable {
    private static final long serialVersionUID = 152785580243082482L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 0:：违规；1：封号
     */
    private Integer type;
    /**
     * 惩罚开始时间
     */
    private String startTime;
    /**
     * 惩罚结束时间
     */
    private String endTime;
    /**
     * 惩罚阶梯（1~7）
     */
    private Integer punishmentLevel;
    /**
     * 惩罚原因
     */
    private String reason;
    /**
     * 被取消的权限
     */
    private String revokedPermissions;

}

