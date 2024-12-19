package com.programmercy.controller;

import com.google.common.base.Preconditions;
import com.programmercy.constant.ExceptionLanguageConstant;
import com.programmercy.domain.service.UserDistributionDomainService;
import com.programmercy.entity.Result;
import com.programmercy.vo.DistributionOfPopularCitiesVO;
import com.programmercy.vo.UserDistributionVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:用户地理位置相关的控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-18 13:44
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@Slf4j
@RequestMapping("/userGeographic")
@SuppressWarnings("unchecked")
public class UserGeographicController {

    @Resource
    UserDistributionDomainService userDistributionDomainService;

    /**
     * 统计用户分布信息
     */
    @GetMapping("/{level}")
    public Result<List<UserDistributionVO>> userDistribution(@PathVariable("level") Integer level,
                                                             @RequestParam(value = "name", required = false) String name) {
        try {
            Preconditions.checkNotNull(level, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
            Preconditions.checkArgument(level >= 0, ExceptionLanguageConstant.GEO_LESS_THEN_EXCEPTION);
            Preconditions.checkArgument(level <= 2, ExceptionLanguageConstant.GEO_GREATER_THEN_EXCEPTION);
            if (name == null && level == 1) {
                name = "中国";
            } else if (name == null && level != 0) {
                Preconditions.checkNotNull(name, ExceptionLanguageConstant.ARGUMENT_NULL_EXCEPTION);
            }
            return Result.ok(userDistributionDomainService.getUserDistributionInfo(level, name));
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserGeographicController:userDistribution:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }

    /**
     * 热门城市分布（前 15 名）
     */
    @GetMapping("/distributionOfPopularCities")
    public Result<DistributionOfPopularCitiesVO> distributionOfPopularCities() {
        try {
            return Result.ok(userDistributionDomainService.distributionOfPopularCities());
        } catch (Exception e) {
            log.error("chenyun-user:controller:UserGeographicController:distributionOfPopularCities:Exception: [{},{}]", e.getMessage(), e.getStackTrace());
            return Result.fail(null);
        }
    }
}
