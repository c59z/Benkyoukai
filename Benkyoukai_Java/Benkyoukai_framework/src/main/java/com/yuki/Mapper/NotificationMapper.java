package com.yuki.Mapper;

import com.yuki.Domain.Entity.NotificationArticle;
import com.yuki.Domain.Entity.Vo.NotificationArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    Long getCountByTargetUserId(Long targetUserId);

    boolean saveNotification(NotificationArticle notification);

    boolean updateNotification(NotificationArticle notification);

    List<NotificationArticleVo> getNotificationByUser(Long userId, int offset, int pageSize);

    List<NotificationArticleVo> getUnreadNotificationByUser(Long userId, int offset, int pageSize);

    int getNotificationCountByUser(Long userId);

    int getUnreadNotificationCountByUser(Long userId);
}
