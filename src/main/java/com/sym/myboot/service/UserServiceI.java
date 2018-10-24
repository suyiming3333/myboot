package com.sym.myboot.service;

import com.sym.myboot.entity.User;

import java.util.List;

public interface  UserServiceI {

    List<User> findAllUser(int pageNum, int pageSize);

    User selectUserByUsername(String username);

}
