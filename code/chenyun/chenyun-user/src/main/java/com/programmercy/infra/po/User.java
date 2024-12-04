package com.programmercy.infra.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author 爱吃小鱼的橙子
 * @since 2024-11-14 14:32:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -68071860159686968L;
    /**
     * 用户唯一标识
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 0：管理员；1：普通用户
     */
    private Integer role;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 注册时间，格式（xxxx/xx/xx）
     */
    private String createdAt;
    /**
     * 关联的 location
     */
    private Long locationId;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 用户状态（0：封号；1：违规；2：正常）
     */
    private Integer userStatus;
    /**
     * 个人简介
     */
    private String information;
    /**
     * 个人简介审核状态（0：待审核；1：已审核）
     */
    private Integer infoStatus;
    /**
     * 待审核个人简介
     */
    private String reviewInfo;

}

