package com.programmercy.enums;

import lombok.Data;

/**
 * Description: 一些固定返回参数的常量
 * Created by 爱吃小鱼的橙子 on 2024-12-18 12:09
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
public enum ReturnParamsEnum {
    /**
     * 00 代表服务器；00：代表通用；XXX：代表业务内容
     */
    SERVER_ERROR("0000001", "服务器出现故障"),

    /**
     * 01 代表鉴权；01： 代表用户模块；XXX：代表业务内容
     */
    AUTH_NOT_ADMIN("0101002", "不是管理员"),
    AUTH_USER_EAL("0101003", "用户已被封号"),
    AUTH_USER_NOT_EXIST("0101004", "用户不存在");

    private String code;
    private String msg;

    ReturnParamsEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
