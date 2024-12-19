package com.programmercy.converter;

import com.programmercy.dto.HotSearchDTO;
import com.programmercy.vo.HotSearchVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: HotSearch 的转换器
 * Created by 爱吃小鱼的橙子 on 2024-12-18 16:53
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public class HotSearchConverter {

    public static List<HotSearchVO> mapDTOlist2VOList(List<HotSearchDTO> hotSearchDTOS) {
        ArrayList<HotSearchVO> hotSearchVOS = new ArrayList<>();
        for (HotSearchDTO hotSearchDTO : hotSearchDTOS) {
            HotSearchVO hotSearchVO = new HotSearchVO();
            BeanUtils.copyProperties(hotSearchDTO, hotSearchVO);
            hotSearchVOS.add(hotSearchVO);
        }
        return hotSearchVOS;
    }
}
