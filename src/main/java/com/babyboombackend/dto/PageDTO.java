package com.babyboombackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "分页请求参数类")
public class PageDTO {
    @Schema(description = "当前页码")
    @Min(value = 1,message = "页码必须大于0")
    private Integer current = 1;

    @Schema(description = "每页大小")
    @Min(value = 1,message = "每页大小必须大于0")
    private Integer size = 10;
}

