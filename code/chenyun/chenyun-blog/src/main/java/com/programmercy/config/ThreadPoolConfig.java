package com.programmercy.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: 线程池配置
 * Created by 爱吃小鱼的橙子 on 2024-12-18 16:13
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 获取 cpu 个数
     */
    int num = Runtime.getRuntime().availableProcessors();

    /**
     * 博客线程池
     * @return
     */
    @Bean("blogPostThreadPool")
    public ExecutorService blogPostThreadPool() {
        AtomicLong threadNum = new AtomicLong(1L);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setThreadFactory(runnable -> new Thread(runnable, "chenyun-blog-post-thread-pool-" + threadNum.getAndIncrement())).build();
        return new ThreadPoolExecutor(2*num, 2*num, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10000), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 标签线程池
     */
    @Bean("tagThreadPool")
    public ExecutorService tagThreadPool() {
        AtomicLong threadNum = new AtomicLong(1L);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setThreadFactory(runnable -> new Thread(runnable, "chenyun-tag-thread-pool-" + threadNum.getAndIncrement())).build();
        return new ThreadPoolExecutor(2*num, 2*num, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10000), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
