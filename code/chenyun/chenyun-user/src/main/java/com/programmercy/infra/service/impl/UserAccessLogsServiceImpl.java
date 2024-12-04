package com.programmercy.infra.service.impl;

import com.programmercy.dto.UserVisitsDTO;
import com.programmercy.infra.po.UserAccessLogs;
import com.programmercy.infra.mapper.UserAccessLogsDao;
import com.programmercy.infra.service.UserAccessLogsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (UserAccessLogs)表服务实现类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 11:44:18
 */
@Service("userAccessLogsService")
public class UserAccessLogsServiceImpl implements UserAccessLogsService {

    @Resource
    private UserAccessLogsDao userAccessLogsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserAccessLogs queryById(Long id) {
        return this.userAccessLogsDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param userAccessLogs 实例对象
     * @return 实例对象
     */
    @Override
    public UserAccessLogs insert(UserAccessLogs userAccessLogs) {
        this.userAccessLogsDao.insert(userAccessLogs);
        return userAccessLogs;
    }

    /**
     * 修改数据
     *
     * @param userAccessLogs 实例对象
     * @return 实例对象
     */
    @Override
    public UserAccessLogs update(UserAccessLogs userAccessLogs) {
        this.userAccessLogsDao.update(userAccessLogs);
        return this.queryById(userAccessLogs.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userAccessLogsDao.deleteById(id) > 0;
    }

    @Override
    public List<UserVisitsDTO> userVisitsInTheLastWeek(String oneWeekAgoStr, String oneDayAgoStr) {
        return this.userAccessLogsDao.queryUserVisitsInTheLastWeek(oneWeekAgoStr, oneDayAgoStr);
    }
}
