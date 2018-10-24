package com.sym.myboot.service;

import com.github.pagehelper.PageHelper;
import com.sym.myboot.entity.User;
import com.sym.myboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserServiceI{

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        System.out.println("load data from mysql");
        return userMapper.selectAllUser();
    }

    @Override
    @Cacheable(key="#p0")
    public User selectUserByUsername(String username) {
        System.out.println("load data from mysql");
        return userMapper.selectUserByUsername(username);
    }

}
