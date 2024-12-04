package com.programmercy.infra.mapper;

import com.programmercy.infra.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-14 14:31:58
 */
@Mapper
public interface UserDao {

    /**
     * 获取所有用户的信息
     * @return
     */
    List<User> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    User queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param user 查询条件
     * @return 对象列表
     */
    List<User> queryAllByLimit(User user);

    /**
     * 统计总行数
     *
     * @param user 查询条件
     * @return 总行数
     */
    long count(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<User> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<User> entities);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 通过分页信息分页查询用户列表
     * @param target
     * @param pageSize
     * @return
     */
    List<User> queryUsersByPageInfo(@Param("target") Long target, @Param("pageSize") Long pageSize);

    /**
     * 批量更新数据
     * @param userIds
     * @return
     */
    int updateBatch(@Param("userIds") List<Long> userIds, @Param("user") User user);

    /**
     * 根据id，查询用户列表
     * @param userIds
     * @return
     */
    List<Integer> queryUsers(@Param("userIds") List<Long> userIds);

    /**
     * 根据分页信息，查询待审核用户列表
     * @param target
     * @param pageSize
     * @return
     */
    List<User> queryUsersAuditByPageInfo(@Param("target") Long target, @Param("pageSize") Long pageSize);

    /**
     * 根据用户昵称和用户状态来搜索,昵称是模糊搜素
     * @param nickname
     * @param userStatus
     * @return
     */
    List<User> queryUsersByNicknameAndStatus(@Param("nickname") String nickname, @Param("userStatus") Integer userStatus,
                                             @Param("target") Long target, @Param("pageSize") Long pageSize);

    /**
     * 根据用户id和用户状态来精确搜索
     * @param userId
     * @param userStatus
     * @return
     */
    User queryUserByIdAndStatus(@Param("userId") String userId, @Param("userStatus") Integer userStatus);

    /**
     * 根据用户昵称进行模糊搜索
     * @param nickname
     * @return
     */
    List<User> queryUsersByNickname(@Param("nickname") String nickname,
                                    @Param("target") Long target,
                                    @Param("pageSize") Long pageSize);

    /**
     * 根据用户id进行精确搜索
     * @param userId
     * @return
     */
    User queryUserById(@Param("userId") String userId);

    /**
     * 根据用户状态来搜索
     * @param userStatus
     * @return
     */
    List<User> queryUsersByStatus(@Param("userStatus") Integer userStatus,
                                  @Param("target") Long target,
                                  @Param("pageSize") Long pageSize);

    /**
     * 根据用户昵称和用户状态计算符合的用户数
     * @param nickname
     * @param userStatus
     * @return
     */
    Long countUsersByNicknameAndStatus(@Param("nickname") String nickname, @Param("userStatus") Integer userStatus);

    /**
     * 根据用户名称和用户状态查看有多少符合的用户
     * @param nickname
     * @return
     */
    Long countUsersByNickname(@Param("nickname") String nickname);

    /**
     * 根据用户名称和用户状态查看有多少符合的用户
     * @param userStatus
     * @return
     */
    Long countUsersByStatus(@Param("userStatus") Integer userStatus);

    /**
     * 根据用户昵称查询待审核用户列表
     * @param nickname
     * @param target
     * @param pageSize
     * @return
     */
    List<User> queryAuditUsersByNickname(@Param("nickname") String nickname, @Param("target") Long target, @Param("pageSize") Long pageSize);

    /**
     * 根据用户昵称查询待审核用户列表中用户总数
     * @param nickname
     * @return
     */
    Long countAuditUsersByNickname(@Param("nickname") String nickname);
}

