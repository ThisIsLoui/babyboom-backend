package com.babyboombackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过knife4j生成接口文档
 * @return
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI document() {
        return new OpenAPI()
                .info(new Info().title("Babyboom接口文档")
                        .description("Babyboom项目接口文档")
                        .version("v0.0.1")
                        .contact(new Contact().name("Loui").email("1258659851@qq.com")));
    }
}
