package com.yuki.Mapper;

import com.yuki.Domain.Entity.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface AccountMapper {

    boolean updatePassword(Long userId, String passwordNew, Date date);

    boolean updateAvatar(String id, String url, Date date);

    boolean updateUserInfo(UserVo userVo);
}
