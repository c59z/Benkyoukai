package com.yuki.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFavorite {
    private Long id;
    private Long userId;
    private Long articleId;
    private Date createTime;
}
