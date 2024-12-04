package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 热门搜索 VO
 * Created by 爱吃小鱼的橙子 on 2024-12-04 14:25
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotSearchVO {

    /**
     * 关键词
     */
    private String name;
    /**
     * 关键词出现的次数
     */
    private Long value;

}
