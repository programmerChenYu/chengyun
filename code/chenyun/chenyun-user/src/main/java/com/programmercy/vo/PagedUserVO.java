package com.programmercy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 用户列表，带分页的
 * Created by 爱吃小鱼的橙子 on 2024-11-25 16:37
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedUserVO extends PageInfoVO{
    /**
     * 用户ID
     */
    private Long key;
    /**
     * 账户创建时间
     */
    private String createdAt;
    /**
     * 地理位置
     */
    private String location;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户状态（0：封号；1：违规；2：正常）
     */
    private Integer userStatus;
    /**
     * 个人简介审核状态（0：待审核；1：已审核）
     */
    private Integer infoStatus;
}
