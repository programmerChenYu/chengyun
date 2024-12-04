package com.programmercy.infra.mapper;

import com.programmercy.dto.UserVisitsDTO;
import com.programmercy.infra.po.UserAccessLogs;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (UserAccessLogs)表数据库访问层
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 11:44:18
 */
public interface UserAccessLogsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAccessLogs queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param userAccessLogs 查询条件
     * @return 对象列表
     */
    List<UserAccessLogs> queryAllByLimit(UserAccessLogs userAccessLogs);

    /**
     * 统计总行数
     *
     * @param userAccessLogs 查询条件
     * @return 总行数
     */
    long count(UserAccessLogs userAccessLogs);

    /**
     * 新增数据
     *
     * @param userAccessLogs 实例对象
     * @return 影响行数
     */
    int insert(UserAccessLogs userAccessLogs);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserAccessLogs> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserAccessLogs> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserAccessLogs> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserAccessLogs> entities);

    /**
     * 修改数据
     *
     * @param userAccessLogs 实例对象
     * @return 影响行数
     */
    int update(UserAccessLogs userAccessLogs);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 获取一周前的用户访问数据
     * @param oneWeekAgoStr
     * @param oneDayAgoStr
     * @return
     */
    List<UserVisitsDTO> queryUserVisitsInTheLastWeek(@Param("oneWeekAgoStr") String oneWeekAgoStr, @Param("oneDayAgoStr") String oneDayAgoStr);
}

