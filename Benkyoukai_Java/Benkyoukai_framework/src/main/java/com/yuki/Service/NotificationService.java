package com.yuki.Service;

import com.yuki.Domain.Entity.Vo.NotificationArticleVo;
import com.yuki.Domain.ResponseResult;

import java.util.List;

public interface NotificationService {
    Long getNotificationByTargetUserId(Long targetUserId);

    List<NotificationArticleVo> getNotificationByUser(Long userId, int page, int pageSize);

    List<NotificationArticleVo> getUnreadNotificationByUser(Long userId, int offset, int pageSize);

    int getNotificationCountByUser(Long userId);

    int getUnreadNotificationCountByUser(Long userId);
}
