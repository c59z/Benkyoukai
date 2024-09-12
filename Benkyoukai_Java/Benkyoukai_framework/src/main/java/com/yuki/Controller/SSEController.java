package com.yuki.Controller;

import com.yuki.Domain.Entity.User;
import com.yuki.Domain.LoginUser;
import com.yuki.Mapper.UserMapper;
import com.yuki.Service.UserService;
import com.yuki.Utils.JwtUtil;
import com.yuki.Utils.RedisCache;
import com.yuki.Utils.UserUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/sse")
public class SSEController {
    // 保存所有用户的 SseEmitter

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;

    private final Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    // 客户端通过该接口订阅通知
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam("token") String token) {
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("token非法");
        }

        String redisKey = "userid:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }

        String currentUserName = loginUser.getUsername();
        System.out.println("当前用户：" + currentUserName);
        Long currentUserId = loginUser.getUser().getId();

        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitters.put(currentUserId, sseEmitter);

        // 监听连接的完成和超时，及时清理已断开的连接
        sseEmitter.onCompletion(() -> sseEmitters.remove(currentUserId));
        sseEmitter.onTimeout(() -> sseEmitters.remove(currentUserId));
        return sseEmitter;
    }

    public void sendNotification(String targetUserId, String message) {
        SseEmitter sseEmitter = sseEmitters.get(Long.valueOf(targetUserId));
        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                e.printStackTrace();
                sseEmitters.remove(targetUserId);
            }
        }
    }

    public void sendNotificationIfUserOnline(Long targetUserId, String message) {
        if (sseEmitters.containsKey(targetUserId)) {
            sendNotification(targetUserId.toString(), message);
        } else {
            System.out.println("用户" + targetUserId + "不在线，消息存储到数据库。");
        }
    }

}
