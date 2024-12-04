package com.programmercy.domain.service.impl;

import com.programmercy.domain.service.UserDistributionDomainService;
import com.programmercy.infra.po.Location;
import com.programmercy.infra.po.User;
import com.programmercy.infra.service.LocationService;
import com.programmercy.infra.service.UserService;
import com.programmercy.vo.DistributionOfPopularCitiesVO;
import com.programmercy.vo.UserDistributionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-18 14:14
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
public class UserDistributionDomainServiceImpl implements UserDistributionDomainService {

    @Resource
    private LocationService locationService;
    @Resource
    private UserService userService;

    @Override
    public DistributionOfPopularCitiesVO distributionOfPopularCities() {
        // 获取用户列表，构建一个 map，城市名为key，用户数为value
        List<User> userList = userService.queryAllUsers();
        ConcurrentHashMap<String, Long> distributionMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, List<Double>> coordinateMap = new ConcurrentHashMap<>();

        // 遍历用户，将用户加入到 map
        for (User user : userList) {
            // 获取用户的地理位置
            Location location = locationService.queryById(user.getLocationId());
            distributionMap.put(location.getCity(), distributionMap.getOrDefault(location.getCity(), 0L)+1L);
            coordinateMap.putIfAbsent(location.getCity(), new ArrayList<>(){{add(Double.valueOf(location.getLongitude()));add(Double.valueOf(location.getLatitude()));}});
        }
        // 对 map 按照用户数量进行排序，由高到低
        ArrayList<Map.Entry<String, Long>> entryArrayList = new ArrayList<>(distributionMap.entrySet());
        entryArrayList.sort(Map.Entry.comparingByValue());

        List<List<Double>> coordinates = new ArrayList<>();
        List<String> citys = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();
        // 取前 15 名
        for (int i = entryArrayList.size()-1; i >= 0; i--) {
            Map.Entry<String, Long> entry = entryArrayList.get(i);
            if (coordinates.size() == 15) {
                break;
            }
            coordinates.add(coordinateMap.get(entry.getKey()));
            citys.add(entry.getKey());
            numbers.add(entry.getValue());
        }
        DistributionOfPopularCitiesVO distributionOfPopularCitiesVO = new DistributionOfPopularCitiesVO();
        distributionOfPopularCitiesVO.setCoordinate(coordinates);
        distributionOfPopularCitiesVO.setCity(citys);
        distributionOfPopularCitiesVO.setNumber(numbers);
        return distributionOfPopularCitiesVO;
    }

    @Override
    public List<UserDistributionVO> getUserDistributionInfo(Integer level, String name) {
        // 1. 获取用户列表
        List<User> userList = userService.queryAllUsers();
        ConcurrentHashMap<String, Long> distributionMap = new ConcurrentHashMap<>();
        // 2. 遍历用户，查看该用户是哪个城市的并在 map 中进行统计
        for (User user : userList) {
            Long locationId = user.getLocationId();
            Location location = locationService.queryById(locationId);
            // 根据级别来进行统计
            if (level.equals(0)) {
                // 国家来排序
                distributionMap.put(location.getCountry(), distributionMap.getOrDefault(location.getCountry(), 0L)+1L);
            } else if (level.equals(1)) {
                // 国内省份排序
                if (!location.getCountry().equals(name)) {
                    continue;
                }
                distributionMap.put(location.getRegion(), distributionMap.getOrDefault(location.getRegion(), 0L)+1L);
            } else {
                // 省内按城市来排序
                if (!location.getRegion().equals(name)) {
                    continue;
                }
                distributionMap.put(location.getCity(), distributionMap.getOrDefault(location.getCity(), 0L)+1L);
            }
        }
        // 3. 对哈希表依据用户人数进行排序，选取前 5 名
        ArrayList<Map.Entry<String, Long>> entryArrayList = new ArrayList<>(distributionMap.entrySet());
        entryArrayList.sort(Map.Entry.comparingByValue());
        List<UserDistributionVO> userDistributionVOList = new ArrayList<>();
        Integer rank = 1;
        for (int i = entryArrayList.size()-1; i >= 0; i--) {
            Map.Entry<String, Long> entry = entryArrayList.get(i);
            if (userDistributionVOList.size() == 5) {
                return userDistributionVOList;
            }
            UserDistributionVO userDistributionVO = new UserDistributionVO();
            userDistributionVO.setKey(rank+"");
            userDistributionVO.setRank(rank++);
            userDistributionVO.setName(entry.getKey());
            userDistributionVO.setUsers(entry.getValue());
            userDistributionVOList.add(userDistributionVO);
        }
        return userDistributionVOList;
    }
}
