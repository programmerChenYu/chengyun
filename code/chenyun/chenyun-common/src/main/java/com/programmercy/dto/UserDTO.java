package com.programmercy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: User 数据传输类
 * Created by 爱吃小鱼的橙子 on 2024-11-29 9:56
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * 用户唯一标识
     */
    private String key;
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
     * 地理位置
     */
    private String location;
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
    /**
     * 不通过原因
     */
    private List<String> reason;

}