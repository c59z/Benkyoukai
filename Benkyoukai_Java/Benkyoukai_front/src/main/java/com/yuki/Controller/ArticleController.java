package com.yuki.Controller;

import com.yuki.Domain.Entity.Article;
import com.yuki.Domain.Entity.Comment;
import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.Entity.dto.PostedArticle;
import com.yuki.Domain.ResponseResult;
import com.yuki.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PreAuthorize("hasAuthority('sys:root')")
    @GetMapping("/test/getAllArticles")
    public ResponseResult testGetAllArticles(){
        System.out.println("有权限");
        List<Article> articles = articleService.getAllArticles();

        return ResponseResult.okResult(200,"获取成功", articles);
    }

    @GetMapping("/Count")
    public ResponseResult getArticleCount(){
        return articleService.getArticleCount();
    }

    /**
     * 获取随机推荐
     * @return
     */
    @GetMapping("/randomRecommend")
    public ResponseResult getRandomRecommend(){
        List<ArticleVo> articles =  articleService.getRandomRecommend();
        return ResponseResult.okResult(200,"获取每日推荐成功",articles);
    }


    /**
     * 获取首页文章列表
     * @param page 当前页
     * @return
     */
    @GetMapping("/getArticles")
    public ResponseResult getArticles(@RequestParam int page){
        int pageSize = 10;
        List<ArticleVo> articles =  articleService.getArticles(page, pageSize);
        return ResponseResult.okResult(200,"获取文章成功",articles);
    }


    /**
     * 获取用户发布的文章列表
     * @param page 当前页
     * @return
     */
    @GetMapping("/getArticlesByUser")
    public ResponseResult getArticlesByUser(@RequestParam int page){
        int pageSize = 10;
        List<ArticleVo> articles =  articleService.getArticlesByUser(page, pageSize);
        return ResponseResult.okResult(200,"获取文章成功",articles);
    }

    /**
     * 获取用户发布的文章列表
     * @param page 当前页
     * @return
     */
    @GetMapping("/getArticlesByUserId")
    public ResponseResult getArticlesByUser(@RequestParam int page, @RequestParam Long userId){
        int pageSize = 10;
        List<ArticleVo> articles =  articleService.getArticlesByUser(page, pageSize, userId);
        return ResponseResult.okResult(200,"获取文章成功",articles);
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @GetMapping("/getArticleDetail/{id}")
    public ResponseResult getArticleDetail(@PathVariable Long id){
//        System.out.println("文章："+id+" 的详情");
        return articleService.getArticleDetail(id);
    }

    /**
     * 上传图片
     * @param file 用户上传的图片
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult uploadPhoto(@RequestParam("file") MultipartFile file){
        return articleService.uploadPhoto(file);
    }

    /**
     * 删除已经上传的图片
     * @param url
     * @return
     */
    @PostMapping("/upload/del/{url}")
    public ResponseResult uploadPhotoDel(@PathVariable String url){
        return articleService.uploadPhotoDel(url);
    }

    /**
     * 发布文章
     * @param article
     * @return
     */
    @PostMapping("/postArticle")
    public ResponseResult postArticle(@RequestBody PostedArticle article){
        boolean b = articleService.postArticle(article);
        if(b){
            return ResponseResult.okResult(200,"发布成功");
        }else{
            return ResponseResult.okResult(400,"发布失败");
        }
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public ResponseResult delArticle(@PathVariable Long id){
        return articleService.delArticle(id);
    }

    /**
     * 修改文章
     * @param article
     * @return
     */
    @PostMapping("/updateArticle")
    public ResponseResult updateArticle(@RequestBody PostedArticle article){
        return articleService.updateArticle(article);
    }

    /**
     * 点赞文章
     * @param id
     * @return
     */
    @PutMapping("/{id}/like")
    public ResponseResult articleLike(@PathVariable Long id){
        return articleService.linkArticle(id);
    }

    /**
     * 添加浏览量
     * @param id
     * @return
     */
    @PutMapping("/{id}/viewed")
    public ResponseResult articleViewed(@PathVariable Long id){
        return articleService.articleViewed(id);
    }


    /**
     * 发布评论
     * @param comment
     * @return
     */
    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return articleService.addComment(comment);
    }

    /**
     * 点赞评论
     * @param id
     * @return
     */
    @PutMapping("/comment/{id}/like")
    public ResponseResult commentLike(@PathVariable Long id){
        return articleService.linkComment(id);
    }

    /**
     * 回复评论
     * @param comment
     * @return
     */
    @PostMapping("/addReply")
    public ResponseResult addReply(@RequestBody Comment comment){
        return articleService.addReply(comment);
    }

    /**
     * 获取文章中的所有评论（递归）
     * @param articleId
     * @return
     */
    @GetMapping("/getArticleDetail/{articleId}/Comments")
    public ResponseResult getCommentsForAnArticle(@PathVariable Long articleId){
        return articleService.getCommentsForAnArticle(articleId);
    }

    @GetMapping("/isLike/{articleId}")
    public ResponseResult isLiked(@PathVariable Long articleId){
        return articleService.isLiked(articleId);
    }

    @GetMapping("/select")
    public ResponseResult selectedArticleByKeyWord(@RequestParam String kw){
        return articleService.selectedArticleByKeyWord(kw);
    }
}
