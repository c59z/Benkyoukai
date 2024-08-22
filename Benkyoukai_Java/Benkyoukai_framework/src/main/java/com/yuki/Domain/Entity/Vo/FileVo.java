package com.yuki.Domain.Entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVo {
    public String name;
    public String type;
    public String filePath;
}
