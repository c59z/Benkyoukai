package com.yuki.Service.impl;

import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Entity.dto.RegisterUser;
import com.yuki.Domain.LoginUser;
import com.yuki.Domain.ResponseResult;
import com.yuki.Mapper.UserMapper;
import com.yuki.Service.UserService;
import com.yuki.Utils.JwtUtil;
import com.yuki.Utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult userLogin(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        // 经过下面这一步,将会调用UserDetailServiceImpl中的loadUserByUsername自定义登录方法
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没有通过,给出对应的提示
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        // 如果认证通过了,使用userid生成一个jwt,jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        // 把完整的用户信息存入redis,userid作为key
        redisCache.setCacheObject("userid:"+id,loginUser);
        // 把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(200,"登录成功",map);
    }

    @Override
    public ResponseResult userLogout() {
        System.out.println("用户退出");
        // 获取封装的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("userid:"+userid);
        return ResponseResult.okResult(200,"退出成功");
    }

    @Override
    public ResponseResult userRegister(RegisterUser user) {
        String username = user.getUsername();
        User tempUser = getUser(username);
        if(!Objects.isNull(tempUser)){
            if (user.getUsername().equals(tempUser.getUsername())){
                return ResponseResult.errorResult(400,"该用户已存在");
            }
        }
        if (!user.getPassword().equals(user.getConfirm())){
            System.out.println(">>>>>>>>>>>>>>>>>>>");
            System.out.println(user.getPassword());
            System.out.println(user.getConfirm());
//            throw new RuntimeException("两次密码不一致");
            return ResponseResult.errorResult(400,"两次密码不一致");
        }
        if (user.getPassword().length()< 6){
            return ResponseResult.errorResult(400,"密码的长度不能小于6");
        }

        user.setCreatedate(LocalDateTime.now());
        User sysUser = new User();
        // 添加用户名
        sysUser.setUsername(user.getUsername());
        // 加密密码
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        sysUser.setPassword(password);
        sysUser.setCreateDate(user.getCreatedate());
        // 添加状态，默认 启用
        sysUser.setStatus(1);
        // 添加权限, 默认 普通用户
        sysUser.setRole_id(2);
        // 保存，实现用户注册
        boolean b = userMapper.insertUser(sysUser);
        System.out.println(b);
        return ResponseResult.okResult(200,"用户注册成功!");
    }
}
