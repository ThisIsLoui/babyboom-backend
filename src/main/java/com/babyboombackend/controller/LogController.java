package com.babyboombackend.controller;

import com.babyboombackend.exception.BaseException;
import com.babyboombackend.service.LogService;
import com.babyboombackend.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "日志管理")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/file")
    @Operation( summary = "上传图片/音频")
    public Result<Long> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
        if (file.isEmpty()){
            throw new BaseException("文件不能为空");
        }
        return logService.uploadFile(file, fileType);
    }
}
