package com.yuki.Service;

import com.yuki.Domain.Entity.Vo.ArticleVo;
import com.yuki.Mapper.ArticleMapper;
import com.yuki.Utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MyScheduledTask {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleMapper articleMapper;

    @Scheduled(cron = "0 0 4 * * ?")
    public void executeTask() {
        String redisKey = "RandomRecommend";
        List<ArticleVo> articleVoList = redisCache.getCacheList(redisKey);
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

    }

}
