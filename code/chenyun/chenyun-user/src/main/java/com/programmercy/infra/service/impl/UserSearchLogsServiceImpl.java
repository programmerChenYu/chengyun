package com.programmercy.infra.service.impl;

import com.programmercy.dto.HotSearchDTO;
import com.programmercy.infra.po.UserSearchLogs;
import com.programmercy.infra.mapper.UserSearchLogsDao;
import com.programmercy.infra.service.UserSearchLogsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (UserSearchLogs)表服务实现类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 14:27:09
 */
@Service("userSearchLogsService")
public class UserSearchLogsServiceImpl implements UserSearchLogsService {

    @Resource
    private UserSearchLogsDao userSearchLogsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserSearchLogs queryById(Long id) {
        return this.userSearchLogsDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param userSearchLogs 实例对象
     * @return 实例对象
     */
    @Override
    public UserSearchLogs insert(UserSearchLogs userSearchLogs) {
        this.userSearchLogsDao.insert(userSearchLogs);
        return userSearchLogs;
    }

    /**
     * 修改数据
     *
     * @param userSearchLogs 实例对象
     * @return 实例对象
     */
    @Override
    public UserSearchLogs update(UserSearchLogs userSearchLogs) {
        this.userSearchLogsDao.update(userSearchLogs);
        return this.queryById(userSearchLogs.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userSearchLogsDao.deleteById(id) > 0;
    }

    @Override
    public List<HotSearchDTO> getHotSearch() {
        return this.userSearchLogsDao.getHotSearch();
    }
}
