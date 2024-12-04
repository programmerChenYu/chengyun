package com.programmercy.controller;

import com.programmercy.domain.service.UserAccessLogsServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.vo.UserVisitsVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 用户访问控制类
 * Created by 爱吃小鱼的橙子 on 2024-12-04 11:38
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@RequestMapping("/access")
@Slf4j
public class UserAccessController {

    @Resource
    private UserAccessLogsServiceDomain userAccessLogsServiceDomain;
    /**
     * 上一周用户访问量
     */
    @GetMapping("/userVisitsInTheLastWeek")
    public Result<List<UserVisitsVO>> userVisitsInTheLastWeek() {
        try {
            return Result.ok(userAccessLogsServiceDomain.userVisitsInTheLastWeek());
        } catch (Exception e) {
            log.error("chenyun-user:UserAccessController:userVisitsInTheLastWeek:Exception:【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }
}
