package com.yuki.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String summary;

    private String categoryTags;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private Long likeCount;

    private Long createBy;

    private String avatar;

    private String username;

    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer delFlag;


}

