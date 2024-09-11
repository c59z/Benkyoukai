package com.yuki.Service.impl;

import com.yuki.Domain.Entity.Vo.NotificationArticleVo;
import com.yuki.Mapper.NotificationMapper;
import com.yuki.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public Long getNotificationByTargetUserId(Long targetUserId) {
        return notificationMapper.getCountByTargetUserId(targetUserId);
    }

    @Override
    public List<NotificationArticleVo> getNotificationByUser(Long userId, int offset, int pageSize) {
        return notificationMapper.getNotificationByUser(userId,offset,pageSize);
    }

    @Override
    public List<NotificationArticleVo> getUnreadNotificationByUser(Long userId, int offset, int pageSize) {
        return notificationMapper.getUnreadNotificationByUser(userId,offset,pageSize);
    }

    @Override
    public int getNotificationCountByUser(Long userId) {
        return notificationMapper.getNotificationCountByUser(userId);
    }

    @Override
    public int getUnreadNotificationCountByUser(Long userId) {
        return notificationMapper.getUnreadNotificationCountByUser(userId);
    }
}
