package com.yuki.Domain.Entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioVo {
    private String audioFileName;
    private String audioData; // Base64 编码的音频数据
    private String subtitleFileName;
    private String subtitleData; // Base64 编码的字幕数据
}
