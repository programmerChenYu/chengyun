package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (UserAccessLogs)实体类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 11:44:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessLogs implements Serializable {
    private static final long serialVersionUID = -11293329076479683L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 访问日期，按天统计
     */
    private String accessDate;
    /**
     * 访问次数，每次访问统计
     */
    private Long visitCount;
    /**
     * 记录创建时间
     */
    private String createdAt;
    /**
     * 更新时间
     */
    private String updatedAt;

}

