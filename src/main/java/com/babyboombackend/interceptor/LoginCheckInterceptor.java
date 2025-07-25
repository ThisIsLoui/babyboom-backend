package com.babyboombackend.interceptor;

import com.babyboombackend.context.BaseContext;
import com.babyboombackend.properties.JwtProperties;
import com.babyboombackend.utils.JwtUtil;
import com.babyboombackend.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录检查拦截器
 */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override // 在请求处理之前进行调用（Controller方法调用之前）, 返回true则放行，返回false则拦截
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        // 从请求头中获取jwt
        String jwt = req.getHeader(jwtProperties.getTokenName());
        // 如果jwt为空，说明未登录
        if(!StringUtils.hasLength(jwt)){
            log.info("未登录，拦截请求");
            Result<String> error = Result.error("未登录");
            // 手动将对象转为json，然后返回（在Controller中这一步是自动进行的）
            resp.setStatus(401);
            resp.addHeader("Content-Type","application/json;charset=utf-8");
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            return false;
        }
        try {
            // 解析jwt，得到用户ID
            Long sub = jwtUtil.parseToken(jwt);
            // 将解析出的用户ID设置到当前线程的ThreadLocal中
            BaseContext.setCurrentId(sub);

        } catch (Exception e) { // jwt解析失败
            log.info("解析令牌失败，拦截请求");
            Result<String> error = Result.error("未登录");
            resp.setStatus(401);
            resp.addHeader("Content-Type","application/json;charset=utf-8");
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            return false;
        }
        log.info("已登录，放行请求");
        return true;
    }
}
