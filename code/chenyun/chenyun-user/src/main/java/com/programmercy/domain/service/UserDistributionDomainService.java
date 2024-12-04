package com.programmercy.domain.service;

import com.programmercy.vo.DistributionOfPopularCitiesVO;
import com.programmercy.vo.UserDistributionVO;

import java.util.List;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-18 14:13
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface UserDistributionDomainService {
    /**
     * 获取用户地理位置的统计信息
     * @param level 不同级别查询不同的地域级别
     *              0：代表查询国家级别
     *              1：代表查询省份级别
     *              2：代表查询城市级别
     * @return
     */
    List<UserDistributionVO> getUserDistributionInfo(Integer level, String name);

    /**
     * 热门城市分布（前 15 名）
     * @return
     */
    DistributionOfPopularCitiesVO distributionOfPopularCities();
}
