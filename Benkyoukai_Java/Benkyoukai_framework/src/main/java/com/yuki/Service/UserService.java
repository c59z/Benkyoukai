package com.yuki.Service;

import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Entity.dto.RegisterUser;
import com.yuki.Domain.ResponseResult;

public interface UserService {
    User getUser(String username);

    ResponseResult userLogin(User user);

    ResponseResult userLogout();

    ResponseResult userRegister(RegisterUser user);
}
