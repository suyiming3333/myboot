package com.sym.myboot.service;

import com.sym.myboot.entity.SysPermission;
import com.sym.myboot.entity.SysRole;
import com.sym.myboot.entity.SysUser;
import com.sym.myboot.entity.User;

import java.util.List;

public interface  UserServiceI {

    List<User> findAllUser(int pageNum, int pageSize);

    User selectUserByUsername(String username);

    User addUser(User user);

    void delUserByName(String username);

    void updateByUsername(String username);

    User updateUser(User user);

    int getUserCnt();

    SysUser findUserByUserName(String username);

    List<SysRole> getSysRoleByUserName(String username);

    List<SysPermission> getSysPermissionByRoleId(int id);
}
