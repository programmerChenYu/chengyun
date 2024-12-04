package com.programmercy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 爱吃小鱼的橙子
 */
@SpringBootApplication
@MapperScan("com.programmerCy.infra.mapper")
@EnableFeignClients
public class BlogApp {
    public static void main(String[] args) {
        // 关闭nacos日志
//        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(BlogApp.class);
    }
}
