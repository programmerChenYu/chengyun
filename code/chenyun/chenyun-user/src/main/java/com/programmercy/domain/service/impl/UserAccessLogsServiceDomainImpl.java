package com.programmercy.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.programmercy.constant.RedisPrefixConstant;
import com.programmercy.converter.UserVisitsConverter;
import com.programmercy.domain.service.UserAccessLogsServiceDomain;
import com.programmercy.dto.UserVisitsDTO;
import com.programmercy.infra.service.UserAccessLogsService;
import com.programmercy.util.RedisUtil;
import com.programmercy.vo.UserVisitsVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description: 用户访问相关的服务的实现类
 * Created by 爱吃小鱼的橙子 on 2024-12-04 11:48
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
@Slf4j
public class UserAccessLogsServiceDomainImpl implements UserAccessLogsServiceDomain {

    @Resource
    private UserAccessLogsService userAccessLogsService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ExecutorService userThreadPool;

    @Override
    public List<UserVisitsVO> userVisitsInTheLastWeek() throws ExecutionException, InterruptedException {
        List<UserVisitsVO> res = null;
        String str = redisUtil.getStr(RedisPrefixConstant.USER_ACCESS_NUM);
        if (str != null) {
            res = JSONObject.parseArray(str, UserVisitsVO.class);
        } else {
            CompletableFuture<List<UserVisitsVO>> userVisitsVOFuture = CompletableFuture.supplyAsync(() -> {
                // 获取一周前的时间
                String oneWeekAgoStr = getOneWeekAgo();
                // 获取前一天的时间
                String oneDayAgoStr = getOneDayAgo();
                List<UserVisitsDTO> userVisitsDTOS = userAccessLogsService.userVisitsInTheLastWeek(oneWeekAgoStr, oneDayAgoStr);
                if (log.isInfoEnabled()) {
                    log.info("chenyun-user:domain:service:impl:UserAccessLogsServiceDomainImpl:userVisitsInTheLastWeek:userVisitsDTOS: [{}]", JSON.toJSONString(userVisitsDTOS));
                }
                return UserVisitsConverter.mapDTOList2VOList(userVisitsDTOS);
            }, userThreadPool);
            CompletableFuture<Long> ttlFuture = CompletableFuture.supplyAsync(() -> {
                //获取当前时间
                ZonedDateTime now = ZonedDateTime.now();
                // 获取第二天的 00:00:00 时间
                ZonedDateTime startOfNextDay = now.plusDays(1).with(LocalTime.MIN);
                // 计算时间差
                return Duration.between(now, startOfNextDay).getSeconds();
            }, userThreadPool);
            userVisitsVOFuture.thenAcceptBothAsync(ttlFuture, (userVisitsVO, ttl) -> {
                redisUtil.setKVTtl(RedisPrefixConstant.USER_ACCESS_NUM, JSON.toJSONString(userVisitsVO), ttl, TimeUnit.SECONDS);
            });
            res = userVisitsVOFuture.get();
        }
        return res;
    }

    /**
     * 获取一周前的时间
     * @return
     */
    private String getOneWeekAgo() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oneWeekAgo = now.minusWeeks(1);
        int year = oneWeekAgo.getYear();
        int month = oneWeekAgo.getMonth().getValue();
        int day = oneWeekAgo.getDayOfMonth();
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("/").append(month).append("/").append(day);
        return sb.toString();
    }

    /**
     * 获取前一天的时间
     * @return
     */
    private String getOneDayAgo() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oneDayAgo = now.minusDays(1);
        int day = oneDayAgo.getDayOfMonth();
        int month = oneDayAgo.getMonth().getValue();
        int year = oneDayAgo.getYear();
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("/").append(month).append("/").append(day);
        return sb.toString();
    }
}
