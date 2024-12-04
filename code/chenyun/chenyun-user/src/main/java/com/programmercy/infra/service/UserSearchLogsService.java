package com.programmercy.infra.service;

import com.programmercy.dto.HotSearchDTO;
import com.programmercy.infra.po.UserSearchLogs;

import java.util.List;

/**
 * (UserSearchLogs)表服务接口
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 14:27:09
 */
public interface UserSearchLogsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserSearchLogs queryById(Long id);

    /**
     * 新增数据
     *
     * @param userSearchLogs 实例对象
     * @return 实例对象
     */
    UserSearchLogs insert(UserSearchLogs userSearchLogs);

    /**
     * 修改数据
     *
     * @param userSearchLogs 实例对象
     * @return 实例对象
     */
    UserSearchLogs update(UserSearchLogs userSearchLogs);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 获取热门搜索
     * @return
     */
    List<HotSearchDTO> getHotSearch();
}
