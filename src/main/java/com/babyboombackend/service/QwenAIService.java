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

    public String summerizeText(String analysisText, String text, String title){
        UserMessage userMessage = UserMessage.from(
                TextContent.from("下面是用户历史拍摄的一张图片的画面描述。这张照片拍摄于，拍摄者是一位父母，这张照片是他们为宝宝拍摄的，同时附上了标题（标题为"
                        + title
                        +"），以及正文（正文为"+ text + "）。现在我需要帮助用户回忆拍摄这张图片时的心情和感受。请你根据这段描述，结合用户的拍摄习惯、风格和心境，生成一段简短的文字描述，帮助用户重温拍摄时的回忆。禁止使用Markdown，必须使用与用户对话的口吻，不要过于做作，应当自然简洁，同时富有情感。不超过三句话，结尾不要使用祝福"),
                TextContent.from(analysisText)
        );
        ChatResponse response = qwenChatModel.chat(userMessage);
        return response.aiMessage().text();
    }
}
