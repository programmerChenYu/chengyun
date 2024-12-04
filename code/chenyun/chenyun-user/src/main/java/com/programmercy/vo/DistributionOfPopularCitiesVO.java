package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 热门城市分布 VO
 * Created by 爱吃小鱼的橙子 on 2024-12-04 10:07
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionOfPopularCitiesVO {

    /**
     * 经纬度
     */
    private List<List<Double>> coordinate;
    /**
     * 城市名
     */
    private List<String> city;
    /**
     * 用户数量
     */
    private List<Long> number;

}
