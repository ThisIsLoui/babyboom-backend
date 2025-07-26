package com.babyboombackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoomVO {
    private LogVO log;
    private String usedImage; // 使用的图片URL
    private String description; // 音频URL
}
