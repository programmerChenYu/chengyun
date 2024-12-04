package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 用户状态改变入参
 * Created by 爱吃小鱼的橙子 on 2024-11-26 11:13
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementOptionVO {

    private Long userId;

    private Integer optionType;
}
