package com.babyboombackend.vo;

import com.babyboombackend.entity.Log;
import com.babyboombackend.entity.LogAudio;
import com.babyboombackend.entity.LogImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogVO extends Log {
    private List<LogAudio> audioList;
    private List<LogImage> imageList;
}
