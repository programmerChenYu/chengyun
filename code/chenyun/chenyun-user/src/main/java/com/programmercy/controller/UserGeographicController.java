package com.programmercy.controller;

import com.google.common.base.Preconditions;
import com.programmercy.domain.service.UserDistributionDomainService;
import com.programmercy.entity.Result;
import com.programmercy.vo.DistributionOfPopularCitiesVO;
import com.programmercy.vo.UserDistributionVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:用户地理位置相关的控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-18 13:44
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@Slf4j
public class UserGeographicController {

    @Resource
    UserDistributionDomainService userDistributionDomainService;

    /**
     * 统计用户分布信息
     */
    @GetMapping("/userDistribution/{level}")
    public Result<List<UserDistributionVO>> userDistribution(@PathVariable("level") Integer level,
                                                             @RequestParam(value = "name", required = false) String name) {
        Preconditions.checkNotNull(level, "参数不能为空");
        Preconditions.checkArgument(level >= 0, "参数不符合规范，查询级别小于 0");
        Preconditions.checkArgument(level <= 2, "参数不符合规范，查询级别高于 2");
        if (name == null && level == 1) {
            name = "中国";
        } else if (name == null && level != 0) {
            Preconditions.checkNotNull(name, "参数不能为空");
        }
        List<UserDistributionVO> userDistributionVOS = userDistributionDomainService.getUserDistributionInfo(level, name);
        return Result.ok(userDistributionVOS);
    }

    /**
     * 热门城市分布（前 15 名）
     */
    @GetMapping("/userGeographic/distributionOfPopularCities")
    public Result<DistributionOfPopularCitiesVO> distributionOfPopularCities() {
        try {
            return Result.ok(userDistributionDomainService.distributionOfPopularCities());
        } catch (Exception e) {
            log.error("chenyun-user:UserGeographicController:distributionOfPopularCities:Exception:【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }
}
