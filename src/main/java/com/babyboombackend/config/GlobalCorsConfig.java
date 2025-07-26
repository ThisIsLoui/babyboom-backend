package com.babyboombackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 匹配所有接口
                        .allowedOriginPatterns("*") // 允许所有域（Spring Boot 2.4+ 推荐使用这个方法）
                        .allowedMethods("*")        // 允许所有方法（GET, POST, PUT, DELETE, etc.）
                        .allowedHeaders("*")        // 允许所有请求头
                        .allowCredentials(true)     // 允许携带 cookie
                        .maxAge(3600);              // 预检请求缓存时间（单位：秒）
            }
        };
    }
}