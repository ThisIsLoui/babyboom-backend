package com.babyboombackend.service;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QwenAIService {

    @Autowired
    private OpenAiChatModel qwenChatModel;

    public String analyzeImage(String imageUrl){
        UserMessage userMessage = UserMessage.from(
                TextContent.from("请你认真观察这张照片，分析照片中的人物外貌、表情、情绪、姿势、行为、穿着、周围环境、天气等特点，并给出详细的描述。不要使用Markdown格式。"),
                ImageContent.from(imageUrl)
        );
        ChatResponse response = qwenChatModel.chat(userMessage);
        return response.aiMessage().text();
    }
}
