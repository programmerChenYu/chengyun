package com.programmercy.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: 线程池配置
 * Created by 爱吃小鱼的橙子 on 2024-12-18 14:12
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 * 对于计算密集型的任务，在拥有 N（CPU）个处理器的服务器上，当线程池的大小为 N+1 时，通常能实现最优的利用率。
 * 对于包含 I/O 操作或者其它阻塞操作的任务，由于线程并不会一直执行，因此线程池的规模应该更大，参考值可以设置为 2N（CPU）。
 * 线程池资源并不是唯一影响线程池大小的资源，还包括内存、文件句柄、套接字句柄和数据库连接等。
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 获取 CPU 数量
     */
    int num = Runtime.getRuntime().availableProcessors();

    /**
     * 用户线程池
     * @return
     */
    @Bean("userThreadExecutor")
    public ExecutorService userThreadExecutor() {
        AtomicLong threadNum = new AtomicLong(1L);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setThreadFactory(runnable -> new Thread(runnable, "chenyun-user-thread-pool-"+threadNum.getAndIncrement())).build();
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10000);
        return new ThreadPoolExecutor(2*num, 2*num, 0L, TimeUnit.MILLISECONDS, blockingQueue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
