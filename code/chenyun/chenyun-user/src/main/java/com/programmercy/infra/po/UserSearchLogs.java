package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (UserSearchLogs)实体类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 14:27:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchLogs implements Serializable {

    private static final long serialVersionUID = 927284725521119905L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 搜索关键词
     */
    private String searchQuery;
    /**
     * 搜索时间
     */
    private String searchTime;
    /**
     * 搜索结果数量
     */
    private Long searchResultCount;

}

