package com.yuki.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kaiwa {
    private int id;
    private String title;
    private String description;
    private String cover;
    private String AudioRootDirPath;
    private int isFile;
    private int parentId;
}
