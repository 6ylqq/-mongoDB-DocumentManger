package com.ylqq.document.controller;

import com.ylqq.document.pojo.User;
import com.ylqq.document.service.*;
import com.ylqq.document.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author ylqq
 */
@Controller
public class UserController {
    /**
     * 用户Service
     */
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @RequestMapping("/home")
    public String home(){
        return "user/home";
    }

    @RequestMapping("/toModifyUser")
    public String totoModifyUser(){
        return "/user/toModify";
    }

    /**
     * 注册新用户
     */
    @RequestMapping("/addUser")
    public String addUser(User user) {
        try {
            if (!userRepository.existsById(user.getUserid()) && !userRepository.findByLoginName(user.getLoginName())) {
                userRepository.insert(user);
                return "login";
            } else {
                return "用户id或者loginName已存在！";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return "/user/addUser";
        }
    }

    /**
     * 修改个人资料
     */
    @RequestMapping("/modifyUser")
    public String modifyUser(User user) {
        try {
            userService.updateById(user);
            return "user/home";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "user/toModify";
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(String newPassword, @org.jetbrains.annotations.NotNull String oldPassword) {
        User sessionUser = (User) session.getAttribute("user");
        //从数据库中取出用户信息,注意，取出来的密码是加密后的
        Optional<User> user = userRepository.findById(sessionUser.getUserid());
        String md5pass = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        //如果两者加密后相等，即输入的密码正确
        if (user.isPresent()) {
            if (Integer.parseInt(md5pass) == user.get().getUserid()) {
                Update update = new Update().set("password", DigestUtils.md5DigestAsHex(newPassword.getBytes()));
                Query query = Query.query(Criteria.where("userid").is(user.get().getUserid()));
                userService.updatePassword(update, query);
            }
        }
        return "redirect:home";
    }
}
