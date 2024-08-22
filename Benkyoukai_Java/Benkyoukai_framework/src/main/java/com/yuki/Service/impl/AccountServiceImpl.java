package com.yuki.Service.impl;

import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Entity.Vo.UserVo;
import com.yuki.Domain.Entity.dto.ChangPasswordDto;
import com.yuki.Domain.LoginUser;
import com.yuki.Domain.ResponseResult;
import com.yuki.Mapper.AccountMapper;
import com.yuki.Mapper.UserMapper;
import com.yuki.Service.AccountService;
import com.yuki.Utils.FileUtil;
import com.yuki.Utils.RedisCache;
import com.yuki.Utils.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${avatar-path}")
    private String uploadDir;

    private int MaximumSize = 1024 * 1024 * 2; // 2MB

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult changePwd(ChangPasswordDto user) {
        String currentUserName = UserUtil.getCurrentUser();
        User userInfo = userMapper.getUser(currentUserName);
        Long userId = userInfo.getId();





        if(user.getPassword().length() < 6){
            return ResponseResult.errorResult(400,"新密码长度不能小于6");
        }

        if(!user.getPassword().equals(user.getConfirm())){
            return ResponseResult.errorResult(400,"两次密码不一致");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordNew = passwordEncoder.encode(user.getPassword());
        Date date = new Date();
        boolean b = accountMapper.updatePassword(userId,passwordNew,date);
        if(b){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            Long userid = loginUser.getUser().getId();
            redisCache.deleteObject("userid:"+userid);
            return ResponseResult.okResult(200,"修改密码成功,请重新登录");
        }else{
            return ResponseResult.errorResult(400,"修改密码失败");
        }
    }

    @Override
    public ResponseResult uploadAvatar(MultipartFile file) {



        if (file.isEmpty()) {
            return new ResponseResult().error(400,"请上传文件!");
        }else if(file.getSize() >= MaximumSize){
            return new ResponseResult().error(400,"文件大小不能大于2MB");
        }else if(!FileUtil.isImageFile(file)){
            return new ResponseResult().error(400,"请上传.jpg或者.png的图片");
        }

        try {
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String username = UserUtil.getCurrentUser();
            User user = userMapper.getUser(username);
            Long id = user.getId();

            String fileExtension = FileUtil.getFileExtension(file.getOriginalFilename());

            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = id.toString() + "_" + timestamp + fileExtension;
            Path filePath = Paths.get(uploadDir, fileName);
            // 保存文件
            Files.write(filePath, file.getBytes());

            String fileVirtualPath = FileUtil.getCurrentHost()+"avatar/" + fileName;
            return ResponseResult.okResult(200,"操作成功",fileVirtualPath);
        }catch (Exception e){
            return ResponseResult.okResult(400,"上传失败",e.getMessage());
        }
    }

    @Override
    public ResponseResult uploadPhotoDel(String url) {
        String[] s = url.split("_");
        String username = UserUtil.getCurrentUser();
        User user = userMapper.getUser(username);
        String id = user.getId().toString();
        if(!id.equals(s[0])){
            return ResponseResult.errorResult(400,"非当前用户上传的图片");
        }
        File file = new File(uploadDir+url);
        if(!file.isFile() || !file.exists()){
            return ResponseResult.errorResult(400,"错误的文件名称!");
        }
        boolean delete = file.delete();
        if(delete){
            return ResponseResult.errorResult(200,"删除成功!");
        }else{
            return ResponseResult.errorResult(400,"删除失败");
        }
    }

    @Override
    public ResponseResult saveAvatar(String url) {
        String[] s = url.split("_");
        String username = UserUtil.getCurrentUser();
        User user = userMapper.getUser(username);
        String id = user.getId().toString();
        if(!id.equals(s[0])){
            return ResponseResult.errorResult(400,"非当前用户上传的图片");
        }
        File file = new File(uploadDir+url);
        if(!file.isFile() || !file.exists()){
            return ResponseResult.errorResult(400,"错误的文件名称!");
        }

        url = FileUtil.getCurrentHost()+"avatar/"+url;
        Date date = new Date();
        boolean b = accountMapper.updateAvatar(id,url,date);

        if(b){
            return ResponseResult.okResult(200,"更换头像成功!");
        }else{
            return ResponseResult.errorResult(200,"更换头像失败");
        }
    }

    @Override
    public ResponseResult getUserInfo() {
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return ResponseResult.okResult(200,"获取用户基本信息成功",userVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserVo userVo) {
        String currentUserName = UserUtil.getCurrentUser();
        User user = userMapper.getUser(currentUserName);
        Long id = user.getId();
        userVo.setId(id);
        Date date = new Date();
        userVo.setUpdateDate(date);
        boolean b = accountMapper.updateUserInfo(userVo);
        if(b){
            return ResponseResult.okResult(200,"修改成功");
        }else{
            return ResponseResult.errorResult(400,"修改失败");
        }
    }

    @Override
    public ResponseResult getUserInfo(Long id) {
        User user = userMapper.getUserById(id);
        if(Objects.isNull(user)){
            return ResponseResult.errorResult(400,"用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return ResponseResult.okResult(200,"获取用户基本信息成功",userVo);
    }

}
