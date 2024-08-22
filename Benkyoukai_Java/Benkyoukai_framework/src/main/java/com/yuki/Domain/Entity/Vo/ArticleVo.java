package com.yuki.Domain.Entity.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    private Long id;

    private String title;

    private String summary;

    private String categoryTags;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private Long likeCount;

    private Long createBy;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
