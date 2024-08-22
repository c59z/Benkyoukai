package com.yuki.Mapper;

import com.yuki.Domain.Entity.Article;
import com.yuki.Domain.Entity.UserFavorite;
import com.yuki.Domain.Entity.Vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserFavoriteMapper {
    boolean addFavorite(@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("date") Date date);
    boolean removeFavorite(@Param("userId") Long userId, @Param("articleId") Long articleId);
    List<ArticleVo> getUserFavorites(@Param("offset")int offset, @Param("pageSize") int pageSize,@Param("userId") Long userId);
    UserFavorite getUserFavorite(@Param("userId") Long userId, @Param("articleId") Long articleId);


}
