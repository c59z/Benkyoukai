package com.yuki.Controller;

import com.yuki.Domain.Entity.Vo.UserVo;
import com.yuki.Domain.Entity.dto.ChangPasswordDto;
import com.yuki.Domain.Entity.dto.RegisterUser;
import com.yuki.Domain.ResponseResult;
import com.yuki.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    AccountService accountService;


    @PostMapping("/changePwd")
    public ResponseResult changePwd(@RequestBody ChangPasswordDto user){
        return accountService.changePwd(user);
    }

    /**
     * 上传头像
     * @param file 用户上传的头像
     * @return
     */
    @PostMapping("/uploadAvatar")
    public ResponseResult uploadAvatar(@RequestParam("file") MultipartFile file){
        return accountService.uploadAvatar(file);
    }

    /**
     * 删除已经上传的头像
     * @param url
     * @return
     */
    @PostMapping("/uploadAvatar/del/{url}")
    public ResponseResult uploadPhotoDel(@PathVariable String url){
        return accountService.uploadPhotoDel(url);
    }

    /**
     * 保存头像
     * @param url
     * @return
     */
    @PostMapping("/saveAvatar/{url}")
    public ResponseResult saveAvatar(@PathVariable String url){
        return accountService.saveAvatar(url);
    }

    /**
     * 获取用户基本信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResponseResult getUserInfo(){
        return accountService.getUserInfo();
    }

    /**
     * 获取用户基本信息
     * @return
     */
    @GetMapping("/getUserInfo/{id}")
    public ResponseResult getUserInfo(@PathVariable Long id){
        return accountService.getUserInfo(id);
    }

    @PutMapping("/update")
    public ResponseResult updateUserInfo(@RequestBody UserVo userVo){
        return accountService.updateUserInfo(userVo);
    }

}
