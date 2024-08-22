package com.yuki.Domain.Entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostedArticle {

    private Long id;

    private String title;

    private String summary;

    private String status;

    private String content;

    private String categoryTags;

    private Long createBy;

    private String thumbnail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
