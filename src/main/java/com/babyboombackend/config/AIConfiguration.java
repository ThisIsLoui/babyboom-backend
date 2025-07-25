package com.babyboombackend.config;

import com.babyboombackend.properties.AIProperties;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AIConfiguration {

    @Autowired
    private AIProperties aiProperties;

    @Bean
    public OpenAiChatModel qwenChatModel(){
        return OpenAiChatModel.builder()
                .baseUrl(aiProperties.getBaseUrl())
                .apiKey(aiProperties.getQwenApiKey())
                .modelName(aiProperties.getQwenModelName())
                .build();
    }
}
