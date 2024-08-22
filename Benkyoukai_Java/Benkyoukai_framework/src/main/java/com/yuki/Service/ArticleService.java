package com.yuki.Service;

import com.yuki.Domain.Entity.Article;
import com.yuki.Domain.Entity.Comment;
import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.Entity.dto.PostedArticle;
import com.yuki.Domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles();

    List<ArticleVo> getRandomRecommend();

    List<ArticleVo> getArticles(int page, int pageSize);

    ResponseResult getArticleDetail(Long id);

    boolean postArticle(PostedArticle article);

    ResponseResult uploadPhoto(MultipartFile file);

    ResponseResult uploadPhotoDel(String url);

    ResponseResult updateArticle(PostedArticle article);

    ResponseResult linkArticle(Long id);

    ResponseResult addComment(Comment comment);

    ResponseResult linkComment(Long id);

    ResponseResult addReply(Comment comment);

    ResponseResult delArticle(Long id);

    ResponseResult getCommentsForAnArticle(Long articleId);

    ResponseResult articleViewed(Long id);

    List<ArticleVo> getArticlesByUser(int page, int pageSize);

    List<ArticleVo> getArticlesByUser(int page, int pageSize, Long userId);

    ResponseResult isLiked(Long articleId);

    ResponseResult getArticleCount();

    ResponseResult selectedArticleByKeyWord(String kw);
}
