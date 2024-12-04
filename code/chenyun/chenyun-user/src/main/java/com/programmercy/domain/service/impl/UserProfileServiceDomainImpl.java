package com.programmercy.domain.service.impl;

import com.programmercy.domain.service.UserProfileServiceDomain;
import com.programmercy.infra.po.Location;
import com.programmercy.infra.po.User;
import com.programmercy.infra.service.LocationService;
import com.programmercy.infra.service.UserService;
import com.programmercy.vo.PageInfoVO;
import com.programmercy.vo.PagedUserVO;
import com.programmercy.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 用户信息相关的服务的实现类
 * Created by 爱吃小鱼的橙子 on 2024-11-25 16:52
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Service
public class UserProfileServiceDomainImpl implements UserProfileServiceDomain {

    @Resource
    private UserService userService;
    @Resource
    private LocationService locationService;

    @Override
    public List<PagedUserVO> pageForAListOfUsers(PagedUserVO pagedUserVO) {
        List<User> usersByPageInfo = userService.queryUsersByPageInfo(pagedUserVO.getCurrentPage(), pagedUserVO.getPageSize());
        List<PagedUserVO> res = new ArrayList<>();
        for (User user : usersByPageInfo) {
            // 只记录用户，不记录管理员
            PagedUserVO userVO = new PagedUserVO();
            BeanUtils.copyProperties(user, userVO);
            // 根据用户的 locationId 查询其所在地理位置
            Location location = locationService.queryById(user.getLocationId());
            StringBuilder sb = new StringBuilder();
            sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
            userVO.setKey(user.getUserId());
            userVO.setLocation(sb.toString());
            userVO.setCurrentPage(pagedUserVO.getCurrentPage()+1);
            res.add(userVO);
        }
        return res;
    }

    @Override
    public Long numberOfUsers() {
        return userService.numberOfUsers();
    }

    @Override
    public Boolean userManagementOptionBatch(List<Long> userIds, Integer optionType) {
        Boolean flag = false;
        List<Integer> usersStatus = userService.queryUsersStatus(userIds);
        switch (optionType) {
            case 0:
                // 判断传过来的数据是否可以进行对应的批量操作
                for (Integer userStatus : usersStatus) {
                    if (userStatus == 0) {
                        return false;
                    }
                }
                // 进行封号处理
                flag = userService.sealedAccountBatch(userIds);
                break;
            case 1:
                for (Integer userStatus : usersStatus) {
                    if (userStatus != 0) {
                        return false;
                    }
                }
                // 进行接触封号处理
                flag = userService.unblockTheAccountBatch(userIds);
                break;
            case 2:
                for (Integer userStatus : usersStatus) {
                    if (userStatus != 2) {
                        return false;
                    }
                }
                // 进行违规处理
                flag = userService.illegalAccountBatch(userIds);
                break;
            case 3:
                for (Integer userStatus : usersStatus) {
                    if (userStatus != 1) {
                        return false;
                    }
                }
                // 进行接触违规处理
                flag = userService.cancelTheIllegalAccountBatch(userIds);
                break;
        }
        return flag;
    }

    @Override
    public Boolean userManagementOption(Long userId, Integer optionType) {
        Boolean flag = false;
        switch (optionType) {
            case 0:
                // 进行封号处理
                flag = userService.sealedAccount(userId);
                break;
            case 1:
                // 进行接触封号处理
                flag = userService.unblockTheAccount(userId);
                break;
            case 2:
                // 进行违规处理
                flag = userService.illegalAccount(userId);
                break;
            case 3:
                // 进行接触违规处理
                flag = userService.cancelTheIllegalAccount(userId);
                break;
        }
        return flag;
    }

    @Override
    public Long getNumberOfUserInfoAudit() {
        return userService.numberOfUsersAudit();
    }

    @Override
    public List<PagedUserVO> getUserInfoAuditList(PageInfoVO pageInfoVO) {
        // 获取待审核用户
        List<User> users = userService.queryUsersAuditByPageInfo(pageInfoVO);
        List<PagedUserVO> res = new ArrayList<>();
        for (User user : users) {
            PagedUserVO userVO = new PagedUserVO();
            BeanUtils.copyProperties(user, userVO);
            // 根据用户的 locationId 查询其所在地理位置
            Location location = locationService.queryById(user.getLocationId());
            StringBuilder sb = new StringBuilder();
            sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
            userVO.setKey(user.getUserId());
            userVO.setLocation(sb.toString());
            res.add(userVO);
        }
        return res;
    }

    @Override
    public UserVO getUserInfoById(Long userId) {
        User user = userService.queryById(userId);
        UserVO userVO = new UserVO();
        userVO.setKey(user.getUserId().toString());
        Location location = locationService.queryById(user.getLocationId());
        StringBuilder sb = new StringBuilder();
        sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
        userVO.setLocation(sb.toString());
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public Boolean refuseUserInfoAudit(UserVO userVO) {
        Long userId = Long.valueOf(userVO.getKey());
        List<String> reason = userVO.getReason();
        String reviewInfo = userVO.getReviewInfo();
        User user = new User();
        user.setUserId(userId);
        user.setReviewInfo(reviewInfo);
        user.setInfoStatus(1);
        for (String type : reason) {
            if (("0").equals(type)) {
                // 头像不合格
                user.setAvatar("");
            } else if (("1").equals(type)) {
                // 个人简介不合格
                user.setInformation("");
            } else {
                // 用户呢称不合格
                user.setNickname("");
            }
        }
        User update = userService.update(user);
        return update.getInfoStatus() == 1;
    }

    @Override
    public Boolean infoAuditConfirm(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setInfoStatus(1);
        User update = userService.update(user);
        return update.getInfoStatus() == 1;
    }

    @Override
    public List<PagedUserVO> searchUser(String nickname, Integer userStatus, Long currentPage, Long pageSize) {
        List<PagedUserVO> res = new ArrayList<>();
        // 1. 两个都有值
        if (nickname != null && nickname != "" && userStatus != null) {
            List<User> userList = userService.queryUsersByNicknameAndStatus(nickname, userStatus, currentPage, pageSize);
            // 1.1 总数
            Long count = userService.countUsersByNicknameAndStatus(nickname, userStatus);
            if (userList == null || userList.isEmpty()) {
                return res;
            } else {
                for (User user : userList) {
                    PagedUserVO userVO = new PagedUserVO();
                    BeanUtils.copyProperties(user, userVO);
                    userVO.setKey(user.getUserId());
                    userVO.setCountOfPage(count);
                    Location location = locationService.queryById(user.getLocationId());
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
                    userVO.setLocation(sb.toString());
                    res.add(userVO);
                }
            }
        } else if (nickname != null && nickname != "") {
            // 2. 没有用户状态, 根据用户昵称来搜索
            List<User> userList = userService.queryUsersByNickname(nickname, currentPage, pageSize);
            Long count = userService.countUsersByNickname(nickname);
            if (userList == null || userList.isEmpty()) {
                return res;
            } else {
                for (User user : userList) {
                    PagedUserVO userVO = new PagedUserVO();
                    BeanUtils.copyProperties(user, userVO);
                    userVO.setKey(user.getUserId());
                    userVO.setCountOfPage(count);
                    Location location = locationService.queryById(user.getLocationId());
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
                    userVO.setLocation(sb.toString());
                    res.add(userVO);
                }
            }
        } else if (userStatus != null) {
            // 3. 根据用户状态来搜索
            List<User> userList = userService.queryUsersByStatus(userStatus, currentPage, pageSize);
            Long count = userService.countUsersByStatus(userStatus);
            if (userList == null) {
                return res;
            } else {
                for (User user : userList) {
                    PagedUserVO userVO = new PagedUserVO();
                    BeanUtils.copyProperties(user, userVO);
                    userVO.setKey(user.getUserId());
                    userVO.setCountOfPage(count);
                    Location location = locationService.queryById(user.getLocationId());
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
                    userVO.setLocation(sb.toString());
                    res.add(userVO);
                }
            }
        }
        return res;
    }

    @Override
    public List<PagedUserVO> getSearchUserInfoAuditList(String nickname, Long currentPage, Long pageSize) {
        List<PagedUserVO> res = new ArrayList<>();
        List<User> users = userService.queryAuditUsersByNickname(nickname, currentPage, pageSize);
        Long count = userService.countAuditUsersByNickname(nickname);
        for (User user : users) {
            PagedUserVO userVO = new PagedUserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setKey(user.getUserId());
            userVO.setCountOfPage(count);
            Location location = locationService.queryById(user.getLocationId());
            StringBuilder sb = new StringBuilder();
            sb.append(location.getCountry()).append('/').append(location.getRegion()).append('/').append(location.getCity());
            userVO.setLocation(sb.toString());
            res.add(userVO);
        }
        return res;
    }
}
