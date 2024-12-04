package com.programmercy.enums;

import lombok.Getter;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-14 16:25
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Getter
public enum ResultCodeEnum {

    OK(200, "请求成功"),
    MOVED_PERMANENTLY(301, "永久重定向"),
    Found(302, "临时重定向"),
    BAD_REQUEST(400, "请求格式不正确或参数有误"),
    UNAUTHORIZED(401, "请求未经授权"),
    Forbidden(403, "用户已认证，但权限不足"),
    NOTFOUND(404, "请求的资源不存在"),
    UNPROCESSABLE_ENTITY(422, "输入的数据不符合要求"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务器不可用"),
    USER_REQUEST_ERROR(4000000, "用户请求参数缺失或者无效"),
    RESOURCE_DUPLICATION(4000001, "用户名或邮箱已存在"),
    INSUFFICIENT_AUTHORITY(4000002, "权限不足，无法访问"),
    ;


    Integer code;
    String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
