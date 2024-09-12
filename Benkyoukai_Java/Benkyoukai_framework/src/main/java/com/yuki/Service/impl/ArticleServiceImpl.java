package com.yuki.Service.impl;

import com.yuki.Controller.SSEController;
import com.yuki.Domain.Entity.*;
import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Domain.Entity.Vo.CommentVo;
import com.yuki.Domain.Entity.dto.PostedArticle;
import com.yuki.Domain.ResponseResult;
import com.yuki.Mapper.ArticleMapper;
import com.yuki.Mapper.NotificationMapper;
import com.yuki.Mapper.UserMapper;
import com.yuki.Service.ArticleService;
import com.yuki.Utils.FileUtil;
import com.yuki.Utils.RedisCache;
import com.yuki.Utils.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Value("${file-save-path}")
    private String uploadDir;

    private int MaximumSize = 1024 * 1024 * 2; // 2MB

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    SSEController sseController;

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Article> getAllArticles() {
        return articleMapper.getAllArticles();
    }

    @Override
    public List<ArticleVo> getRandomRecommend() {
//        return articleMapper.getRandomRecommend();
        // 1.查看redis数据库中RandomRecommend字段是否存在
        String redisKey = "RandomRecommend";
        List<ArticleVo> articleVoList = redisCache.getCacheList(redisKey);
        // 2.如果存在直接从数据库中获取四篇文章
        if(!Objects.isNull(articleVoList)){
            return articleVoList;
        }
        // 3.如果不存在,从数据库中获取所有的id List<Integer>
        List<Integer> idList = articleMapper.getAllId();
        // 4.根据id随机选取4个值,组合成新的Vo
        int count = 0;
        articleVoList = new ArrayList<>();
        while (count < 4){
            Random random = new Random();
            int randomIndex = random.nextInt(idList.size()); // 获取随机的id
            ArticleVo articleVo = articleMapper.getArticleById(idList.get(randomIndex));
            articleVoList.add(articleVo);
            idList.remove(randomIndex);
            count++;
        }
        // 5.把数据放到redis中
        redisCache.setCacheList(redisKey,articleVoList);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> getArticles(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<ArticleVo> articleVoList = articleMapper.selectArticlesByPagination(offset,pageSize);
        return articleVoList;
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {

        Article article = articleMapper.getArticleDetail(id);
        if(Objects.isNull(article)){
            return ResponseResult.errorResult(400,"文章不存在");
        }else{
            return ResponseResult.okResult(200,"文章详情获取成功",article);
        }
    }

    @Override
    public boolean postArticle(PostedArticle article) {

        if(Objects.isNull(article.getTitle()) ||
                Objects.isNull(article.getContent()) ||
                Objects.isNull(article.getCategoryTags())
        ){
            return false;
        }

        String username = UserUtil.getCurrentUser();
        User user = userMapper.getUser(username);
        Long id = user.getId();
        article.setCreateBy(id);
        article.setStatus("1");
        // 设置发布日期
        Date date = new Date();
        article.setCreateTime(date);
        return articleMapper.insertArticle(article);
    }

    @Override
    public ResponseResult uploadPhoto(MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseResult().error(400,"请上传文件!");
        }else if(file.getSize() >= MaximumSize){
            return new ResponseResult().error(400,"文件大小不能大于2MB");
        }

        try {
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String username = UserUtil.getCurrentUser();
            User user = userMapper.getUser(username);
            Long id = user.getId();

            String fileExtension = FileUtil.getFileExtension(file.getOriginalFilename());

            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = id.toString() + "_" + timestamp + fileExtension;
            Path filePath = Paths.get(uploadDir, fileName);
            // 保存文件
            Files.write(filePath, file.getBytes());

            String fileVirtualPath = FileUtil.getCurrentHost() + fileName;
            return ResponseResult.okResult(200,"操作成功",fileVirtualPath);
        }catch (Exception e){
            return ResponseResult.okResult(400,"上传失败",e.getMessage());
        }

    }

    @Override
    public ResponseResult uploadPhotoDel(String url) {
        String[] s = url.split("_");
        String username = UserUtil.getCurrentUser();
        User user = userMapper.getUser(username);
        String id = user.getId().toString();
        if(!id.equals(s[0])){
            return ResponseResult.errorResult(400,"非当前用户上传的图片");
        }
        File file = new File(uploadDir+url);
        if(!file.isFile() || !file.exists()){
            return ResponseResult.errorResult(400,"错误的文件名称!");
        }
        boolean delete = file.delete();
        if(delete){
            return ResponseResult.errorResult(200,"删除成功!");
        }else{
            return ResponseResult.errorResult(400,"删除失败");
        }
    }

    @Override
    public ResponseResult updateArticle(PostedArticle article) {

        if(Objects.isNull(article.getTitle()) ||
                Objects.isNull(article.getContent()) ||
                Objects.isNull(article.getCategoryTags())
        ){
            return ResponseResult.errorResult(400,"标题,正文,标签不能为空");
        }

        Long id = article.getId();
        Article articleTemp = articleMapper.getArticleDetail(id);

        if(Objects.isNull(articleTemp)){
            return ResponseResult.errorResult(400,"原文章不存在");
        }
        if(!Objects.equals(article.getId(), articleTemp.getId())){
            return ResponseResult.errorResult(400,"非本人发布的文章");
        }
        Date originalCreateTime = articleTemp.getCreateTime();
        BeanUtils.copyProperties(article,articleTemp);
        Date date = new Date();
        articleTemp.setUpdateTime(date);
        articleTemp.setCreateTime(originalCreateTime);
        boolean b = articleMapper.updateArticle(articleTemp);
        if(b){
            return ResponseResult.okResult(200,"修改成功!");
        }else{
            return ResponseResult.errorResult(400,"修改失败");
        }
    }

    @Override
    public ResponseResult linkArticle(Long id) {
        Article articleDetail = articleMapper.getArticleDetail(id);
        if (Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"您要点赞的文章不存在");
        }
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        String currentUsername = user.getUsername();
        Long userId = user.getId();
        Articlelike articlelike = articleMapper.getArticleLike(userId,id);
        ResponseResult result = new ResponseResult();

        if(Objects.isNull(articlelike)){
            Date date = new Date();
            try {
                // 用户没有给这个文章点过赞，插入一条数据
                boolean b = articleMapper.insertArticleLike(userId,id,date);
                if(b){
                    result = ResponseResult.okResult(200,"点赞成功!",1);
                    // 插入一条消息
                    saveNotification(id, userId, currentUsername);
                }else{
                    result = ResponseResult.okResult(400,"点赞失败",0);
                }
            }catch (Exception e){
                result = ResponseResult.errorResult(400,e.getMessage());
            }
        }else{
            int st = Integer.parseInt(articlelike.getState());
            if(st == 1){
                // 用户给这个文章点过赞，取消点赞
                boolean b = articleMapper.updateArticleLikeState(userId,id,0L);
                if(b){
                    updateNotification(id,userId,currentUsername,"0");
                    result = ResponseResult.okResult(200,"取消点赞成功!",0);
                }else{
                    result = ResponseResult.okResult(400,"取消点赞失败",1);
                }
            }else{
                boolean b = articleMapper.updateArticleLikeState(userId,id,1L);
                if(b){
                    updateNotification(id,userId,currentUsername,"1");
                    result = ResponseResult.okResult(200,"点赞成功!",1);
                }else{
                    result = ResponseResult.okResult(400,"点赞失败",0);
                }
            }
        }

        Long likeCount = articleMapper.getLikeCount(id);
        articleMapper.updateLikeCount(id, likeCount);

        return result;

    }

    /**
     *
     * @param id 文章id
     * @param currentUserId 当前用户的id
     * @param currentUsername 当前用户的用户名
     * @return
     */
    private boolean saveNotification(Long id,Long currentUserId,String currentUsername){
        Date date = new Date();
        // 获取文章的作者
        Long targetUserId = articleMapper.getUserIdByArticleId(id);
        Article articleDetail = articleMapper.getArticleDetail(id);
        System.out.println(currentUserId);
        System.out.println(targetUserId);
        // 点赞的人和文章作者相同不发送消息
        if(currentUserId.equals(targetUserId)){
            return false;
        }

        // 插入一条通知
        NotificationArticle notification = new NotificationArticle();
        notification.setCreateTime(date);
        notification.setUserId(currentUserId);
        notification.setTargetUserId(targetUserId);
        notification.setArticleId(id);
        String msg = currentUsername+" 给你的文章["+articleDetail.getTitle()+"]点赞了";
        notification.setMassage(msg);
        boolean b = notificationMapper.saveNotification(notification);
        if(b){
            System.out.println("发送消息:"+msg);
            sseController.sendNotificationIfUserOnline(targetUserId,msg);
        }
        return b;
    }



    private boolean updateNotification(Long id,Long currentUserId,String currentUsername,String state){
        Date date = new Date();
        // 获取文章的作者
        Long targetUserId = articleMapper.getUserIdByArticleId(id);
        Article articleDetail = articleMapper.getArticleDetail(id);
        // 点赞的人和文章作者相同不发送消息
        if(Objects.equals(currentUserId, targetUserId)){
            return false;
        }
        // 插入一条通知
        NotificationArticle notification = new NotificationArticle();
        notification.setCreateTime(date);
        notification.setUserId(currentUserId);
        notification.setTargetUserId(targetUserId);
        notification.setArticleId(id);
        notification.setState(state);
        if(state.equals("1")){
            notification.setMassage(currentUsername+" 给你的文章["+articleDetail.getTitle()+"]点赞了");
        }
        return notificationMapper.updateNotification(notification);
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(Objects.isNull(comment.getContent())){
            return ResponseResult.errorResult(400,"评论的内容不能为空");
        }
        Long articleId = comment.getArticleId();
        Article articleDetail = articleMapper.getArticleDetail(articleId);
        if(Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"要评论的文章不存在");
        }
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        Long id = user.getId();
        Date date = new Date();
        comment.setUserId(id);
        comment.setCreateTime(date);
        boolean b = articleMapper.insertComment(comment);
        if(b){
            return ResponseResult.okResult(200,"评论成功!");
        }else{
            return ResponseResult.errorResult(400,"评论失败!");
        }
    }

    @Override
    public ResponseResult linkComment(Long id) {
        Comment comment = articleMapper.getComment(id);
        if (Objects.isNull(comment)){
            return ResponseResult.errorResult(400,"您要点赞的评论不存在");
        }
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        Long userId = user.getId();
        Date date = new Date();
        Commentlike commentLike = articleMapper.getCommentLike(userId,id);
        if(Objects.isNull(commentLike)){
            boolean b = articleMapper.insertCommentLike(userId,id,date);
            if(b){
                return ResponseResult.okResult(200,"点赞评论成功!");
            }else{
                return ResponseResult.okResult(200,"点赞评论失败!");
            }
        }else{
            int state = Integer.parseInt(commentLike.getState());
            if(state == 1){
                // 取消点赞
                boolean b = articleMapper.updateCommentLikeState(userId,id,0L);
                if(b){
                    return ResponseResult.okResult(200,"取消点赞评论成功!");
                }else{
                    return ResponseResult.okResult(400,"取消点赞评论失败");
                }
            }else{
                // 再次点赞
                boolean b = articleMapper.updateCommentLikeState(userId,id,1L);
                if(b){
                    return ResponseResult.okResult(200,"点赞评论成功!");
                }else{
                    return ResponseResult.okResult(400,"点赞评论失败");
                }
            }
        }
    }

    @Override
    public ResponseResult addReply(Comment comment) {

        Long articleId = comment.getArticleId();
        Article articleDetail = articleMapper.getArticleDetail(articleId);
        if(Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"要回复的评论所在的文章不存在");
        }

        Long parentId = comment.getParentId();
        Comment comment_temp = articleMapper.getComment(parentId);
        if(Objects.isNull(comment_temp)){
            return ResponseResult.errorResult(400,"要回复的评论不存在");
        }



        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        Long id = user.getId();
        Date date = new Date();
        comment.setUserId(id);
        comment.setCreateTime(date);
        boolean b = articleMapper.insertReply(comment);
        if(b){
            return ResponseResult.okResult(200,"回复成功!");
        }else{
            return ResponseResult.errorResult(400,"回复失败!");
        }
    }

    @Override
    public ResponseResult delArticle(Long id) {
        Article articleDetail = articleMapper.getArticleDetail(id);
        if(Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"您要删除的文章不存在");
        }
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        Long currentUserId = user.getId();
        Long createdUserId = articleDetail.getCreateBy();
        if(!Objects.equals(currentUserId, createdUserId) && user.getRole_id() != 1){
            return ResponseResult.errorResult(400,"这不是您发布的文章,无法删除");
        }

        boolean b = articleMapper.deleteArticle(id);
        if(b){
            return ResponseResult.okResult(200,"删除成功");
        }else{
            return ResponseResult.errorResult(400,"删除文章失败");
        }
    }

    @Override
    public ResponseResult getCommentsForAnArticle(Long articleId) {
        Article articleDetail = articleMapper.getArticleDetail(articleId);
        if(Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"文章不存在或已被删除");
        }
        // 获取这个文章的所有顶级目录
        List<CommentVo> commentsTop = articleMapper.getCommentsByArticleId(articleId);

        // 创建一个Set来跟踪已经处理过的评论ID,防止死循环
        Set<Long> processedCommentIds = new HashSet<>();

        for (CommentVo commentVo : commentsTop) {
            populateLikesAndReplies(commentVo,processedCommentIds);
        }

        // 这里的数据已经完整了，如何把顶级目录的评论按照id倒叙排序？
        List<CommentVo> collect = commentsTop.stream()
                .sorted(Comparator.comparing(CommentVo::getId).reversed())
                .collect(Collectors.toList());

        return ResponseResult.okResult(200,"获取评论成功",collect);
    }

    @Override
    public ResponseResult articleViewed(Long id) {

        Article articleDetail = articleMapper.getArticleDetail(id);
        if(Objects.isNull(articleDetail)){
            return ResponseResult.errorResult(400,"文章不存在");
        }

        boolean b = articleMapper.updateViewCount(id);
        if(b){
            return ResponseResult.okResult(200,"viewed");
        }else{
            return ResponseResult.errorResult(400,"viewed error");
        }
    }

    @Override
    public List<ArticleVo> getArticlesByUser(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String currentUser = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUser);
        Long userId = user.getId();
        List<ArticleVo> articleVoList = articleMapper.selectArticlesByPaginationAndUser(offset,pageSize,userId);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> getArticlesByUser(int page, int pageSize, Long userId) {
        int offset = (page - 1) * pageSize;
        List<ArticleVo> articleVoList = articleMapper.selectArticlesByPaginationAndUser(offset,pageSize,userId);
        return articleVoList;
    }

    @Override
    public ResponseResult isLiked(Long articleId) {
        Long userId = 0L;
        try {
            String currentUserName = UserUtil.getCurrentUser();
            User user = userMapper.getUser(currentUserName);
            userId = user.getId();
        }catch (Exception e){
            return ResponseResult.errorResult(400,"error");
        }

        Articlelike articleLike = articleMapper.getArticleLike(userId,articleId);
        if(Objects.isNull(articleLike)){
            return ResponseResult.okResult(200,"success",0);
        }else{
            if(articleLike.getState().equals("1")){
                return ResponseResult.okResult(200,"success",1);
            }else{
                return ResponseResult.okResult(200,"success",0);
            }
        }
    }

    @Override
    public ResponseResult getArticleCount() {
        Long count = articleMapper.getArticleCount();
        return ResponseResult.okResult(200,"success",count);
    }

    @Override
    public ResponseResult selectedArticleByKeyWord(String kw) {
        System.out.println("关键字:"+kw);
        if(Objects.isNull(kw)){
            return ResponseResult.errorResult(400,"请输入要查询的关键字");
        }
        List<ArticleVo> articleVoList = articleMapper.getArticlesByKeyWord(kw);
        if(Objects.isNull(articleVoList)){
            return ResponseResult.errorResult(404,"未找到您要查询的文章");
        }
        return ResponseResult.okResult(200,"success",articleVoList);
    }

    private void populateLikesAndReplies(CommentVo comment, Set<Long> processedCommentIds) {
        // 如果这个评论已经处理过，直接返回
        if (processedCommentIds.contains(comment.getId())) {
            return;
        }
        // 标记这个评论为已处理
        processedCommentIds.add(comment.getId());

        // 获取评论的点赞数
        int likeCount = articleMapper.getLikeCountByCommentId(comment.getId());
        comment.setLikeCount(likeCount);
        // 获取评论的用户信息
        Long userId = comment.getUserId();
        User user = userMapper.getUserById(userId);
        comment.setUsername(user.getUsername());
        comment.setAvatar(user.getAvatar());
        comment.setNickname(user.getNickname());

        // 递归处理嵌套评论
        List<CommentVo> replies = articleMapper.getCommentsByParentId(comment.getId());
        for (CommentVo reply : replies) {
            populateLikesAndReplies(reply, processedCommentIds);
        }
        comment.setReplies(replies);
    }
}
