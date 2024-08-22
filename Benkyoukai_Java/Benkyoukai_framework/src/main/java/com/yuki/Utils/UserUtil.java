package com.yuki.Utils;

import com.yuki.Domain.Entity.User;
import com.yuki.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {


    public static String getCurrentUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name;
    }
}
