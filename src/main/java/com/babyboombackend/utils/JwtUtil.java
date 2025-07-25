package com.babyboombackend.utils;

import com.babyboombackend.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 创建Token
     * @param sub 用户ID
     * @return
     */
    public String createToken(Long sub) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()),Jwts.SIG.HS256) // 配置密钥，和算法
                .subject(sub.toString()) // 设置签发给的用户ID
                .expiration(new Date(System.currentTimeMillis()+jwtProperties.getTtl() * 1000)) // 设置过期时间，1小时
                .compact();
    }

    /**
     * 解析Token
     * @param token token字符串
     * @return 用户ID
     */
    public Long parseToken(String token) {
        return Long.parseLong(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}
