package com.yuki.Controller;

import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.ResponseResult;
import com.yuki.Service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class UserFavoriteController {

    @Autowired
    private UserFavoriteService userFavoriteService;

    @PostMapping("/add")
    public ResponseResult addFavorite(@RequestParam Long articleId) {
        return userFavoriteService.addFavorite(articleId);
    }

    @PostMapping("/remove")
    public ResponseResult removeFavorite(@RequestParam Long articleId) {
        return userFavoriteService.removeFavorite(articleId);
    }

    @GetMapping("/list/{userId}/{page}")
    public ResponseResult getUserFavorites(@PathVariable Long userId,@PathVariable int page) {
        List<ArticleVo> favorites = userFavoriteService.getUserFavorites(userId,page);
        return ResponseResult.okResult(200, "获取收藏列表成功", favorites);
    }

    @GetMapping("/isFavorite/{articleId}")
    public ResponseResult isFavorite(@PathVariable Long articleId){
        return userFavoriteService.isFavorite(articleId);
    }


}
