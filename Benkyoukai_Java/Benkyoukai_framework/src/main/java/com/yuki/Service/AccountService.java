package com.yuki.Service;

import com.yuki.Domain.Entity.Vo.UserVo;
import com.yuki.Domain.Entity.dto.ChangPasswordDto;
import com.yuki.Domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    public ResponseResult changePwd(ChangPasswordDto user);

    ResponseResult uploadAvatar(MultipartFile file);

    ResponseResult uploadPhotoDel(String url);

    ResponseResult saveAvatar(String url);

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(UserVo userVo);

    ResponseResult getUserInfo(Long id);
}
