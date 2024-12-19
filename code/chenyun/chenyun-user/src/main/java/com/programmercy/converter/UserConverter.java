package com.programmercy.converter;

import com.programmercy.infra.po.Location;
import com.programmercy.infra.po.User;
import com.programmercy.infra.service.LocationService;
import com.programmercy.vo.PagedUserVO;
import com.programmercy.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: User 转换器
 * Created by 爱吃小鱼的橙子 on 2024-12-19 9:27
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Component
public class UserConverter {

    @Resource
    private LocationService locationService;

    /**
     * 将 User List 映射为 PagedUserVO List
     * @param users
     * @return
     */
    public List<PagedUserVO> mapPOList2PagedUserVOList(List<User> users, Long currentPage, Long count) {
        List<PagedUserVO> res = new ArrayList<>();
        for (User user : users) {
            // 只记录用户，不记录管理员
            PagedUserVO userVO = new PagedUserVO();
            BeanUtils.copyProperties(user, userVO);
            // 根据用户的 locationId 查询其所在地理位置
            Location location = locationService.queryById(user.getLocationId());
            StringBuilder sb = new StringBuilder();
            sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
            userVO.setKey(user.getUserId());
            userVO.setLocation(sb.toString());
            userVO.setCurrentPage(currentPage+1);
            userVO.setCountOfPage(count);
            res.add(userVO);
        }
        return res;
    }

    public UserVO mapPO2VO(User user) {
        UserVO userVO = new UserVO();
        userVO.setKey(user.getUserId().toString());
        Location location = locationService.queryById(user.getLocationId());
        StringBuilder sb = new StringBuilder();
        sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
        userVO.setLocation(sb.toString());
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
