package com.programmercy.infra.service;

import com.programmercy.dto.UserVisitsDTO;
import com.programmercy.infra.po.UserAccessLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (UserAccessLogs)表服务接口
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 11:44:18
 */
public interface UserAccessLogsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAccessLogs queryById(Long id);

    /**
     * 新增数据
     *
     * @param userAccessLogs 实例对象
     * @return 实例对象
     */
    UserAccessLogs insert(UserAccessLogs userAccessLogs);

    /**
     * 修改数据
     *
     * @param userAccessLogs 实例对象
     * @return 实例对象
     */
    UserAccessLogs update(UserAccessLogs userAccessLogs);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 获取一周前的用户访问数据
     * @param oneWeekAgoStr
     * @param oneDayAgoStr
     * @return
     */
    List<UserVisitsDTO> userVisitsInTheLastWeek(String oneWeekAgoStr, String oneDayAgoStr);
}
