package com.babyboombackend.service;

import com.babyboombackend.context.BaseContext;
import com.babyboombackend.entity.LogAudio;
import com.babyboombackend.entity.LogImage;
import com.babyboombackend.exception.BaseException;
import com.babyboombackend.mapper.LogAudioMapper;
import com.babyboombackend.mapper.LogImageMapper;
import com.babyboombackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LogService {

    @Autowired
    private MinioService minioService;

    @Autowired
    private LogAudioMapper logAudioMapper;
    @Autowired
    private LogImageMapper logImageMapper;

    public Result<Long> uploadFile(MultipartFile file, String fileType) {
        String url = minioService.uploadFile(file);
        Long userId = BaseContext.getCurrentId();
        if (url == null) {
            throw new BaseException("上传失败");
        }
        if (fileType.equals("image")) {
            // 创建日志图片对象
            LogImage logImage = LogImage.builder()
                    .url(url)
                    .userId(userId)
                    .build();
            // 插入到数据库
            int insertResult = logImageMapper.insert(logImage);
            if (insertResult <= 0) {
                throw new BaseException("保存图片信息失败");
            }
            // 返回图片ID
            return Result.success(logImage.getId());
        } else {
            // 创建日志音频对象
            LogAudio logAudio = LogAudio.builder()
                    .url(url)
                    .userId(userId)
                    .build();
            // 插入到数据库
            int insertResult = logAudioMapper.insert(logAudio);
            if (insertResult <= 0) {
                throw new BaseException("保存音频信息失败");
            }
            // 返回音频ID
            return Result.success(logAudio.getId());
        }
    }
}
