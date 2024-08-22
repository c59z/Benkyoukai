package com.yuki.Mapper;

import com.yuki.Domain.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(Long id);
    User getUser(String username);

    List<String> getUserPermissions(Long id);

    boolean insertUser(User user);
}
