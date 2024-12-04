package com.programmercy.controller;

import com.programmercy.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 用户认证控制类
 * Created by 爱吃小鱼的橙子 on 2024-11-14 16:50
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
public class UserAuthController {

//    public Result<> userRegistration()

    @GetMapping("/test")
    public Result<String> test() {
        return Result.ok("你好");
    }
}
