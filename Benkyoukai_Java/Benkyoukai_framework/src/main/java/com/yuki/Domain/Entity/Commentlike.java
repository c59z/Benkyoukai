package com.yuki.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentlike implements Serializable {
    private Long id;

    private Long articleId;

    private Long userId;

    private Date createTime;

    private String state;
}
