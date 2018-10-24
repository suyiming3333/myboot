package com.sym.myboot.mapper;

import com.sym.myboot.entity.User;


import java.util.List;

public interface UserMapper {

    List<User> selectAllUser();

    User selectUserByUsername(String username);
}
