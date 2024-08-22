package com.yuki.Controller;

import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Entity.dto.RegisterUser;
import com.yuki.Domain.ResponseResult;

import com.yuki.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return userService.userLogin(user);
    }

    @PostMapping("/logout")
    public ResponseResult userLogout(){
        return userService.userLogout();
    }

    @PostMapping("/register")
    public ResponseResult userLogin(@RequestBody RegisterUser user){
        return userService.userRegister(user);
    }

//    @PreAuthorize("hasAuthority('sys:root')")
    @PostMapping("/test")
    public ResponseResult test(){
        return ResponseResult.okResult(200,"只有登录了才能看见");
    }
}
