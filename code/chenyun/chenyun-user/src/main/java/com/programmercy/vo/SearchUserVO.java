package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 搜索用户
 * Created by 爱吃小鱼的橙子 on 2024-11-27 9:20
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserVO extends PageInfoVO {

    /**
     * 查询的用户昵称/用户ID
     */
    private String searchValue;
    /**
     * 查询的用户帐号状态
     * 0: 封号
     * 1: 违规
     * 2: 正常
     */
    private String statusValue;

}
