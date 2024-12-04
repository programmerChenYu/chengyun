package com.programmercy.entity;

import com.programmercy.enums.ResultCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 全局统一的返回参数
 * Created by 爱吃小鱼的橙子 on 2024-11-14 16:20
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Data
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public static Result ok() {
        Result result = new Result();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMessage(ResultCodeEnum.OK.getMessage());
        return result;
    }

    public static <T> Result ok(T data) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMessage(ResultCodeEnum.OK.getMessage());
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        result.setMessage(ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage());
        return result;
    }

    public static <T> Result fail(T data) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        result.setMessage(ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage());
        result.setData(data);
        return result;
    }
}
