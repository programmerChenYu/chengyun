package com.programmercy.domain.service.impl;

import com.programmercy.domain.service.UserAccessLogsServiceDomain;
import com.programmercy.dto.UserVisitsDTO;
import com.programmercy.infra.service.UserAccessLogsService;
import com.programmercy.vo.UserVisitsVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 用户访问相关的服务的实现类
 * Created by 爱吃小鱼的橙子 on 2024-12-04 11:48
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
public class UserAccessLogsServiceDomainImpl implements UserAccessLogsServiceDomain {

    @Resource
    private UserAccessLogsService userAccessLogsService;

    @Override
    public List<UserVisitsVO> userVisitsInTheLastWeek() {
        // 获取一周前的时间
        String oneWeekAgoStr = getOneWeekAgo();
        // 获取前一天的时间
        String oneDayAgoStr = getOneDayAgo();
        List<UserVisitsDTO> userVisitsDTOS = userAccessLogsService.userVisitsInTheLastWeek(oneWeekAgoStr, oneDayAgoStr);
        ArrayList<UserVisitsVO> res = new ArrayList<>();
        for (UserVisitsDTO userVisitsDTO : userVisitsDTOS) {
            UserVisitsVO userVisitsVO = new UserVisitsVO();
            BeanUtils.copyProperties(userVisitsDTO, userVisitsVO);
            res.add(userVisitsVO);
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
