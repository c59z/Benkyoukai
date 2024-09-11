package com.yuki.Mapper;

import com.yuki.Domain.Entity.Article;
import com.yuki.Domain.Entity.Articlelike;
import com.yuki.Domain.Entity.Comment;
import com.yuki.Domain.Entity.Commentlike;
import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.Entity.Vo.CommentVo;
import com.yuki.Domain.Entity.dto.PostedArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleMapper {

    List<Article> getAllArticles();

    List<ArticleVo> getRandomRecommend();

    List<ArticleVo> selectArticlesByPagination(@Param("offset")int offset, @Param("pageSize") int pageSize);

    Article getArticleDetail(@Param("id")Long id);

    boolean insertArticle(PostedArticle article);

    boolean updateArticle(Article articleTemp);

    Articlelike getArticleLike(Long userId, Long id);

    Long getLikeCount(Long articleId);

    boolean updateLikeCount(Long articleId,Long count);

    boolean insertArticleLike(Long userId, Long id, Date date);

    boolean updateArticleLikeState(Long userId, Long id, Long state);

    boolean insertComment(Comment comment);

    Comment getComment(Long id);

    Commentlike getCommentLike(Long userId, Long id);

    boolean insertCommentLike(Long userId, Long id,Date date);

    boolean updateCommentLikeState(Long userId, Long id, Long state);

    boolean insertReply(Comment comment);

    boolean deleteArticle(Long id);

    List<CommentVo> getCommentsByArticleId(Long articleId);

    int getLikeCountByCommentId(Long commentId);

    List<CommentVo> getCommentsByParentId(Long id);

    boolean updateViewCount(Long id);

    List<ArticleVo> selectArticlesByPaginationAndUser(int offset, int pageSize, Long userId);

    Long getArticleCount();

    List<ArticleVo> getArticlesByKeyWord(String kw);

    List<Integer> getAllId();

    ArticleVo getArticleById(int randomIndex);

    Long getUserIdByArticleId(Long id);
}
