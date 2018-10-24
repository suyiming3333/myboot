package com.sym.myboot.controller;


import com.sym.myboot.config.MyProperty;
import com.sym.myboot.dao.UserRepository;
import com.sym.myboot.entity.User;
import com.sym.myboot.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @RequestMapping("/test")
    public String sayHello(){
        String info = myProperty.getEmail().toString()+myProperty.getPhone().toString();
        System.out.println(info);
//        User user = userRepository.findAllById(1);
        User user = userService.selectUserByUsername("ayue");
        //List<User> list =  userService.findAllUser(0,1);
        return "hi myboot hhh";
    }

    @RequestMapping("/list2")
    public String userList2(Model model) throws Exception {
        model.addAttribute("hello","Hello, My Boot!");
        model.addAttribute("userList", userService.findAllUser(0,5));
        return "list";
    }


}
