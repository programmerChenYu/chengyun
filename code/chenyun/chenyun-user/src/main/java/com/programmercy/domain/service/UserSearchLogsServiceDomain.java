package com.programmercy.domain.service;

import com.programmercy.vo.HotSearchVO;

import java.util.List;

/**
 * Description: 用户搜索记录服务接口
 * Created by 爱吃小鱼的橙子 on 2024-12-04 14:29
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public interface UserSearchLogsServiceDomain {

    /**
     * 获取热门搜索
     * @return
     */
    List<HotSearchVO> getHotSearch();
}
