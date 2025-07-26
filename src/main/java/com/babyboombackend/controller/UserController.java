package com.babyboombackend.controller;

import com.babyboombackend.dto.CreateUserDTO;
import com.babyboombackend.entity.User;
import com.babyboombackend.service.UserService;
import com.babyboombackend.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "个人中心")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @Operation(summary = "注册/登录宝宝")
    public Result<String> registerUser(@RequestBody @Valid CreateUserDTO user) {
        // 这里可以添加注册或登录的逻辑
        return userService.login(user);
    }

    @GetMapping()
    @Operation(summary = "获取当前宝宝信息")
    public Result<User> getCurrentUser() {
        return userService.getCurrentUser();
    }

}
