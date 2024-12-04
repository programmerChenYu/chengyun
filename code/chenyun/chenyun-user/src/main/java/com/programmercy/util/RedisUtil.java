package com.programmercy.util;

import com.hankcs.hanlp.HanLP;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Description: 封装的 redis 工具类
 * Created by 爱吃小鱼的橙子 on 2024-11-14 15:43
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
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

}
