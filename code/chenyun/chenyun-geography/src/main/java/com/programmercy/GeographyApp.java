package com.programmercy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 爱吃小鱼的橙子
 */
@SpringBootApplication
@MapperScan("com.programmerCy.infra.mapper")
public class GeographyApp
{
    public static void main(String[] args) {
        SpringApplication.run(GeographyApp.class);
    }
}
