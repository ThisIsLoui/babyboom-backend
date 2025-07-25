package com.babyboombackend.service;

import com.babyboombackend.dto.CreateUserDTO;
import com.babyboombackend.entity.User;
import com.babyboombackend.mapper.UserMapper;
import com.babyboombackend.utils.JwtUtil;
import com.babyboombackend.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public Result<String> login(CreateUserDTO user){
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        int id = userMapper.insert(newUser);
        if (id > 0) {
            // 注册成功，生成Token
            String token = jwtUtil.createToken(newUser.getId());
            return Result.success(token);
        } else {
            // 注册失败
            return Result.error("注册失败，请稍后再试");
        }
    }
}
