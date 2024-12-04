package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 用户状态批量改变入参
 * Created by 爱吃小鱼的橙子 on 2024-11-26 11:49
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManagementOptionBatchVO {

    private List<Long> userIds;

    private Integer optionType;

}
