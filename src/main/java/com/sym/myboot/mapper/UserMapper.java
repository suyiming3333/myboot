package com.sym.myboot.mapper;

import com.sym.myboot.entity.User;


import java.util.List;

public interface UserMapper {

    List<User> selectAllUser();

    User selectUserByUsername(String username);

    int addUser(User user);

    void delUserByName(String username);

    void updateByUsername(String username);

    void updateUser(User user);
}
