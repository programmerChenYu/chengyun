package com.programmercy.converter;

import com.programmercy.dto.UserVisitsDTO;
import com.programmercy.vo.UserVisitsVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: UserVisits 转换器
 * Created by 爱吃小鱼的橙子 on 2024-12-18 17:15
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public class UserVisitsConverter {

    public static List<UserVisitsVO> mapDTOList2VOList(List<UserVisitsDTO> userVisitsDTOS) {
        List<UserVisitsVO> userVisitsVOS = new ArrayList<>();
        for (UserVisitsDTO userVisitsDTO : userVisitsDTOS) {
            UserVisitsVO userVisitsVO = new UserVisitsVO();
            BeanUtils.copyProperties(userVisitsDTO, userVisitsVO);
            userVisitsVOS.add(userVisitsVO);
        }
        return userVisitsVOS;
    }
}
