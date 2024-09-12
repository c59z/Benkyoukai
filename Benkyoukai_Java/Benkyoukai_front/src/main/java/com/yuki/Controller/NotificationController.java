package com.yuki.Controller;

import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Entity.Vo.NotificationArticleVo;
import com.yuki.Domain.ResponseResult;
import com.yuki.Service.NotificationService;
import com.yuki.Service.UserService;
import com.yuki.Utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @GetMapping("/get")
    public ResponseResult getNotificationByUser(@RequestParam Integer page,
                                                @RequestParam(required = false) Integer unread){
        if(page <= 0){
            page = 1;
        }
        int pageSize = 10;
        String currentUser = UserUtil.getCurrentUser();
        User user = userService.getUser(currentUser);
        Long userId = user.getId();
        int offset = pageSize * (page - 1);
        List<NotificationArticleVo> list = new ArrayList<>();
        int totalRecords;
        if(!Objects.isNull(unread)){
            totalRecords = notificationService.getUnreadNotificationCountByUser(userId);
            list = notificationService.getUnreadNotificationByUser(userId,offset,pageSize);
        }else{
            totalRecords = notificationService.getNotificationCountByUser(userId);
            list = notificationService.getNotificationByUser(userId,offset,pageSize);
        }
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalPages", totalPages);
        return ResponseResult.okResult(200,"success",result);
    }




}
