package com.yuki.Service.impl;

import com.yuki.Domain.Entity.Article;
import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Entity.UserFavorite;
import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.ResponseResult;
import com.yuki.Mapper.ArticleMapper;
import com.yuki.Mapper.UserFavoriteMapper;
import com.yuki.Mapper.UserMapper;
import com.yuki.Service.UserFavoriteService;
import com.yuki.Utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {

    @Autowired
    UserFavoriteMapper userFavoriteMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public ResponseResult addFavorite(Long articleId) {
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        if(Objects.isNull(user)){
            return ResponseResult.errorResult(400,"用户不存在");
        }
        Long userId = user.getId();

        Article articleDetail = articleMapper.getArticleDetail(articleId);
        if(Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"文章不存在");
        }

        UserFavorite userFavorite = userFavoriteMapper.getUserFavorite(userId, articleId);
        if(!Objects.isNull(userFavorite)){
            return ResponseResult.errorResult(400,"您已收藏该文章,无需再次收藏");
        }

        Date date = new Date();

        boolean b = userFavoriteMapper.addFavorite(userId, articleId, date);

        if(b){
            return ResponseResult.okResult(200,"收藏成功!",1);
        }else{
            return ResponseResult.okResult(400,"收藏失败",0);
        }
    }

    @Override
    public ResponseResult removeFavorite(Long articleId) {
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        if(Objects.isNull(user)){
            return ResponseResult.errorResult(400,"用户不存在");
        }
        Long userId = user.getId();


        boolean b = false;

        UserFavorite userFavorite = userFavoriteMapper.getUserFavorite(userId, articleId);
        if(!Objects.isNull(userFavorite)){
            b = userFavoriteMapper.removeFavorite(userId, articleId);
        }else{
            return ResponseResult.errorResult(400,"您未收藏该文章或该文章不存在");
        }


        if(b){
            return ResponseResult.okResult(200,"删除成功!",0);
        }else{
            return ResponseResult.okResult(400,"删除失败",1);
        }
    }

    @Override
    public List<ArticleVo> getUserFavorites(Long userId, int page) {
        User userById = userMapper.getUserById(userId);
        if(Objects.isNull(userById)){
            throw new RuntimeException("用户不存在");
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        List<ArticleVo> userFavorites = userFavoriteMapper.getUserFavorites(offset, pageSize, userId);
        return userFavorites;
    }

    @Override
    public ResponseResult isFavorite(Long articleId) {
        Long userId = 0L;
        try {
            String currentUserName = UserUtil.getCurrentUser();
            User user = userMapper.getUser(currentUserName);
            userId = user.getId();
        }catch (Exception e){
            return ResponseResult.errorResult(400,"未知错误");
        }

        UserFavorite userFavorite = userFavoriteMapper.getUserFavorite(userId, articleId);
        if(Objects.isNull(userFavorite)){
            return ResponseResult.okResult(200,"success",0);
        }else{
            return ResponseResult.okResult(200,"success",1);
        }
    }
}
