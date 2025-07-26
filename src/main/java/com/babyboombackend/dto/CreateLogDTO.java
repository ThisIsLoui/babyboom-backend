package com.babyboombackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLogDTO {
    @Schema(description = "标题内容")
    private String title;

    @Schema(description = "文字内容")
    private String text;

    @Schema(description = "图片列表")
    private List<Long> imageList;

    @Schema(description = "音频列表")
    private List<Long> audioList;
}
