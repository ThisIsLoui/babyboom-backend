package com.babyboombackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @Schema(description = "生日")
    @NotNull(message = "生日不能为空")
    private LocalDate birthday;

    @Schema(description = "昵称")
    @NotNull(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "性别", allowableValues = "1,2")
    @NotNull(message = "性别不能为空")
    private Integer gender; // 1:男, 2:女

    @Schema(description = "身高（单位：厘米）")
    @NotNull(message = "身高不能为空")
    private Double height;

    @Schema(description = "体重（单位：千克）")
    @NotNull(message = "体重不能为空")
    private Double weight;
}
