package com.babyboombackend.service;

import com.babyboombackend.config.RabbitMQConfig;
import com.babyboombackend.context.BaseContext;
import com.babyboombackend.dto.CreateLogDTO;
import com.babyboombackend.dto.ImageAnalysisDTO;
import com.babyboombackend.entity.Log;
import com.babyboombackend.entity.LogAudio;
import com.babyboombackend.entity.LogImage;
import com.babyboombackend.exception.BaseException;
import com.babyboombackend.mapper.LogAudioMapper;
import com.babyboombackend.mapper.LogImageMapper;
import com.babyboombackend.mapper.LogMapper;
import com.babyboombackend.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);
    @Autowired
    private MinioService minioService;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private LogAudioMapper logAudioMapper;
    @Autowired
    private LogImageMapper logImageMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    @Transactional
    public Result<Long> createLog(CreateLogDTO createLogDTO) {
        // 获取当前用户ID
        Long userId = BaseContext.getCurrentId();
        // 创建日志实体
        Log log = Log.builder()
                .userId(userId)
                .text(createLogDTO.getText())
                .build();
        // 插入日志到数据库
        int insertResult = logMapper.insert(log);
        if (insertResult <= 0) {
            throw new BaseException("创建日志失败");
        }
        // 操作audio列表
        if (createLogDTO.getAudioList() != null) {
            for (Long audioId : createLogDTO.getAudioList()) {
                logAudioMapper.updateById(LogAudio.builder()
                        .id(audioId)
                        .logId(log.getId())
                        .build());
            }
        }
        // 操作image列表
        if (createLogDTO.getImageList() != null) {
            for (Long imageId : createLogDTO.getImageList()) {
                logImageMapper.updateById(LogImage.builder()
                        .id(imageId)
                        .logId(log.getId())
                        .build());
                LogImage logImage = logImageMapper.selectById(imageId);
                // 加入AI解析列表
                ImageAnalysisDTO imageAnalysisDTO = ImageAnalysisDTO.builder()
                        .imageId(imageId)
                        .imageUrl(logImage.getUrl())
                        .build();
                rabbitTemplate.convertAndSend(RabbitMQConfig.IMAGE_ANALYSIS_QUEUE_NAME, imageAnalysisDTO);
            }
        }
        // 返回新创建的日志ID
        return Result.success(log.getId());
    }
}
