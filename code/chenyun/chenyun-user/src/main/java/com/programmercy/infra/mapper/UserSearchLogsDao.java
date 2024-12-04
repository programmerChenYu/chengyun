package com.programmercy.infra.mapper;

import com.programmercy.dto.HotSearchDTO;
import com.programmercy.infra.po.UserSearchLogs;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (UserSearchLogs)表数据库访问层
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-12-04 14:27:09
 */
public interface UserSearchLogsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserSearchLogs queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param userSearchLogs 查询条件
     * @return 对象列表
     */
    List<UserSearchLogs> queryAllByLimit(UserSearchLogs userSearchLogs);

    /**
     * 统计总行数
     *
     * @param userSearchLogs 查询条件
     * @return 总行数
     */
    long count(UserSearchLogs userSearchLogs);

    /**
     * 新增数据
     *
     * @param userSearchLogs 实例对象
     * @return 影响行数
     */
    int insert(UserSearchLogs userSearchLogs);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserSearchLogs> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserSearchLogs> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserSearchLogs> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserSearchLogs> entities);

    /**
     * 修改数据
     *
     * @param userSearchLogs 实例对象
     * @return 影响行数
     */
    int update(UserSearchLogs userSearchLogs);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 获取热门搜索
     * @return
     */
    List<HotSearchDTO> getHotSearch();
}

