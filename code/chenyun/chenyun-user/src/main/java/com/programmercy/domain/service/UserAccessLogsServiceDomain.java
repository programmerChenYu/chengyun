package com.programmercy.domain.service;

import com.programmercy.vo.UserVisitsVO;

import java.util.List;

/**
 * Description: 用户访问相关的服务
 * Created by 爱吃小鱼的橙子 on 2024-12-04 11:47
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface UserAccessLogsServiceDomain {

    /**
     * 上一周用户访问量
     * @return
     */
    List<UserVisitsVO> userVisitsInTheLastWeek();
}
