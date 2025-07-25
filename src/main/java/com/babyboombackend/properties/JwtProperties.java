package com.babyboombackend.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "babyboom.jwt")
public class JwtProperties {
    private String secretKey;
    private Long ttl;
    private String tokenName;
}
