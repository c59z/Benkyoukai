package com.yuki.Service;

import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.ResponseResult;

import java.util.List;

public interface UserFavoriteService {
    ResponseResult addFavorite(Long articleId);
    ResponseResult removeFavorite(Long articleId);
    List<ArticleVo> getUserFavorites(Long userId, int page);

    ResponseResult isFavorite(Long articleId);

}
