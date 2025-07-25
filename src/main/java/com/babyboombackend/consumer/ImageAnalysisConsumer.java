package com.babyboombackend.consumer;

import com.babyboombackend.config.RabbitMQConfig;
import com.babyboombackend.dto.ImageAnalysisDTO;
import com.babyboombackend.entity.LogImage;
import com.babyboombackend.mapper.LogImageMapper;
import com.babyboombackend.service.QwenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageAnalysisConsumer {

    @Autowired
    private QwenAIService qwenAIService;

    @Autowired
    private LogImageMapper logImageMapper;

    @RabbitListener(queues = RabbitMQConfig.IMAGE_ANALYSIS_QUEUE_NAME)
    public void handleImageAnalysis(ImageAnalysisDTO message) {
        String imageUrl = message.getImageUrl();
        Long imageId = message.getImageId();
        try {
            String analysis = qwenAIService.analyzeImage(imageUrl);
            LogImage logImage = LogImage.builder()
                    .id(imageId)
                    .analysis(analysis)
                    .build();
            logImageMapper.updateById(logImage);
        } catch (Exception e) {
            log.error("图像分析任务失败: {}", message, e);
        }
    }
}
