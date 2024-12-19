package com.programmercy.util;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description: 封装的 redis 工具类
 * Created by 爱吃小鱼的橙子 on 2024-11-14 15:43
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据 key 获取 value
     * @param key
     * @return
     */
    public String getKV(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
