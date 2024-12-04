package com.programmercy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 爱吃小鱼的橙子
 */
@SpringBootApplication
@MapperScan("com.programmerCy.infra.mapper")
public class UserApp {
    public static void main(String[] args) {
        // 关闭nacos日志
//        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(UserApp.class);
    }
}
