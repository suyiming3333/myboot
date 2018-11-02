package com.sym.myboot.controller;


import com.sym.myboot.config.MyProperty;
import com.sym.myboot.dao.UserRepository;
import com.sym.myboot.entity.User;
import com.sym.myboot.service.UserServiceI;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 *RestController 会直接返回字符串 导致无法返回页面
 */

@RestController
public class TestController {

    @Autowired
    private MyProperty myProperty;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private UserRepository userRepository;

    @RequiresRoles("admin1")
    @RequiresPermissions("userind")
    @RequestMapping("/test")
    public String sayHello(){
        String info = myProperty.getEmail().toString()+myProperty.getPhone().toString();
        System.out.println(info);
//        User user = userRepository.findAllById(1);
        User user = userService.selectUserByUsername("dingdu");
        //List<User> list =  userService.findAllUser(0,1);
        return user.toString();
    }

    @RequestMapping("/list2")
    public String userList2(Model model) throws Exception {
        model.addAttribute("hello","Hello, My Boot!");
        model.addAttribute("userList", userService.findAllUser(0,5));
        return "list";
    }

    @RequestMapping("/addUser")
    public int addUser(){
        User user = new User();
        user.setAge(34);
        user.setUsername("james harden 6");
        userService.addUser(user);
        return user.getId();
    }

    @RequestMapping("/delUser")
    public void delUserByName(){
        userService.delUserByName("ayue");
    }

    @RequestMapping("/updateUser")
    public void updateUser(){
        User user = new User(19,"dingdu",200);
        userService.updateUser(user);
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

    @RequestMapping("/getUserCnt")
    public int getUserCnt(){
        return userService.getUserCnt();
    }


}
