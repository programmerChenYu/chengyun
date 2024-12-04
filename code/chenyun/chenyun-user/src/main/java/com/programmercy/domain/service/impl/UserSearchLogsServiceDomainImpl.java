package com.programmercy.domain.service.impl;

import com.programmercy.domain.service.UserSearchLogsServiceDomain;
import com.programmercy.dto.HotSearchDTO;
import com.programmercy.infra.service.UserSearchLogsService;
import com.programmercy.vo.HotSearchVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 用户搜索记录服务接口实现类
 * Created by 爱吃小鱼的橙子 on 2024-12-04 14:29
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
public class UserSearchLogsServiceDomainImpl implements UserSearchLogsServiceDomain {

    @Resource
    private UserSearchLogsService userSearchLogsService;

    @Override
    public List<HotSearchVO> getHotSearch() {
        List<HotSearchDTO> hotSearchDTOS = userSearchLogsService.getHotSearch();
        ArrayList<HotSearchVO> res = new ArrayList<>();
        for (HotSearchDTO hotSearchDTO : hotSearchDTOS) {
            HotSearchVO hotSearchVO = new HotSearchVO();
            BeanUtils.copyProperties(hotSearchDTO, hotSearchVO);
            res.add(hotSearchVO);
        }
        return res;
    }
}
