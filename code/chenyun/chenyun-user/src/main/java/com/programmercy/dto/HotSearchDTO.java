package com.programmercy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-12-04 14:33
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotSearchDTO {

    /**
     * 关键词
     */
    private String name;
    /**
     * 关键词出现的次数
     */
    private Long value;

}
