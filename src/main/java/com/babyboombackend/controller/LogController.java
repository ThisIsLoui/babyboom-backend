package com.babyboombackend.controller;

import com.babyboombackend.dto.CreateLogDTO;
import com.babyboombackend.dto.GetLogDTO;
import com.babyboombackend.exception.BaseException;
import com.babyboombackend.service.LogService;
import com.babyboombackend.vo.BoomVO;
import com.babyboombackend.vo.LogVO;
import com.babyboombackend.vo.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "日志管理")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/file")
    @Operation(summary = "上传图片/音频")
    public Result<Long> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
        if (file.isEmpty()){
            throw new BaseException("文件不能为空");
        }
        return logService.uploadFile(file, fileType);
    }

    @PostMapping
    @Operation(summary = "创建日志")
    public Result<Long> createLog(@RequestBody CreateLogDTO createLogDTO) {
        // 这里可以添加创建日志的逻辑
        // 例如保存到数据库等
        return logService.createLog(createLogDTO);
    }

    @PostMapping("list")
    @Operation(summary = "获取日志")
    public Result<List<LogVO>> getLog(@RequestBody GetLogDTO getLogDTO){
        return logService.getLog(getLogDTO);
    }

    @GetMapping("boom")
    @Operation(summary = "获取记忆盲盒")
    public Result<BoomVO> getBoom(){
        return logService.getBoom();
    }

}
