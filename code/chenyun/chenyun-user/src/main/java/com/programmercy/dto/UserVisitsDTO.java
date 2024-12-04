package com.programmercy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-12-04 12:10
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVisitsDTO {

    /**
     * 日期
     */
    private String day;
    /**
     * 访问量
     */
    private Long visits;

}
