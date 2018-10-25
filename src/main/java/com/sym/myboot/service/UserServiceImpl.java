package com.sym.myboot.service;

import com.github.pagehelper.PageHelper;
import com.sym.myboot.entity.User;
import com.sym.myboot.mapper.master.UserMapper;
import com.sym.myboot.mapper.slave.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "userService")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserServiceImpl implements UserServiceI{

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Autowired
    private UserMapper2 userMapper2;//这里会报错，但是并不会影响

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        System.out.println("load data from mysql");
        return userMapper.selectAllUser();
    }

    @Override
    @Cacheable(value = "user",key="#p0")
    public User selectUserByUsername(String username) {
        System.out.println("load data from mysql");
        return userMapper.selectUserByUsername(username);
    }

    @Override
    @CachePut(value = "user",key = "#user.username")
    public User addUser(User user) {
        System.out.println("add User");
        userMapper.addUser(user);
        return user;
    }

    @Override
    @CacheEvict(value = "user",key="#p0")
    public void delUserByName(String username){
        System.out.println("del user both mysql and redis");
        userMapper.delUserByName(username);
    }

    @Override
    @CachePut(value = "user",key = "#p0")
    public void updateByUsername(String username){
        System.out.println("update user both mysql and redis");
        userMapper.updateByUsername(username);
    }

    @Override
    @CachePut(value = "user",key = "#user.username")
    public User updateUser(User user) {
        System.out.println("update user both mysql and redis");
        userMapper.updateUser(user);
        return user;
    }

    public int getUserCnt(){
        return userMapper2.getUserCnt();
    }

}
