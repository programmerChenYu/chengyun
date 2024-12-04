package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 用户访问量 VO
 * Created by 爱吃小鱼的橙子 on 2024-12-04 11:40
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVisitsVO {

    /**
     * 日期
     */
    private String day;
    /**
     * 访问量
     */
    private Long visits;

}
