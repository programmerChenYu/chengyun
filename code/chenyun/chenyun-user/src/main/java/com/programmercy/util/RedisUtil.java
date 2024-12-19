package com.programmercy.util;

import cn.dev33.satoken.stp.StpUtil;
import com.hankcs.hanlp.HanLP;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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
     * 基于 redis 的各个表的发号器
     * @param key
     * @return
     */
    public Long SignalSender(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    /**
     * string 类型的存入操作，存入后验证是否存入成功，并设置过期时间
     * @param key
     * @param value
     * @return
     */
    public Boolean setKVTtl(String key, String value, Long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
        return stringRedisTemplate.opsForValue().get(key).equals(value);
    }

    /**
     * string 类型的获取
     */
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
