package com.programmercy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关启动器
 */
@SpringBootApplication
public class GatewayApp
{
    public static void main( String[] args )
    {
        // 关闭nacos日志
//        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(GatewayApp.class);
    }
}
