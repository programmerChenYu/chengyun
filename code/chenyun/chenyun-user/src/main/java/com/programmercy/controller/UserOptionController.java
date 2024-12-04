package com.programmercy.controller;

import com.programmercy.domain.service.UserSearchLogsServiceDomain;
import com.programmercy.entity.Result;
import com.programmercy.vo.HotSearchVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 用户操作控制类
 * Created by 爱吃小鱼的橙子 on 2024-12-04 14:24
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@RestController
@RequestMapping("/option")
@Slf4j
public class UserOptionController {

    @Resource
    private UserSearchLogsServiceDomain userSearchLogsServiceDomain;

    /**
     * 获取热门搜索
     */
    @GetMapping("/getHotSearch")
    public Result<List<HotSearchVO>> getHotSearch() {
        try {
            return Result.ok(userSearchLogsServiceDomain.getHotSearch());
        } catch (Exception e) {
            log.error("chenyun-user:UserOptionController:getHotSearch:Exception:【{}】", e.getStackTrace());
            return Result.fail(null);
        }
    }
}
