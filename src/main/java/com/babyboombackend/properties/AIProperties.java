package com.babyboombackend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "babyboom.ai")
@Data
public class AIProperties {
    private String baseUrl;
    private String qwenModelName;
    private String qwenApiKey;
}
