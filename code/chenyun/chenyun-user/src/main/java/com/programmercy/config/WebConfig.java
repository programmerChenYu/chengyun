package com.programmercy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: 文件头
 * Created by 爱吃小鱼的橙子 on 2024-11-19 10:04
 * Created with IntelliJ IDEA.
 * @author 爱吃小鱼的橙子
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(24*3600);
    }
}
