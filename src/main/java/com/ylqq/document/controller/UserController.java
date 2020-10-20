package com.ylqq.document.controller;

import com.ylqq.document.pojo.User;
import com.ylqq.document.service.*;
import com.ylqq.document.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author ylqq
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 用户Service
     */
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 注册新用户
     */
    @RequestMapping("/addUser")
    public String addUser(User user) {
        try {
            if (!userRepository.existsById(user.getUserid()) && !userRepository.findByLoginName(user.getLoginName())) {
                userRepository.insert(user);
                return "forward:/login.html";
            } else {
                return "用户id或者loginName已存在！";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return "forward:/addUser.html";
        }
    }

    /**
     * 修改个人资料
     */
    @RequestMapping("/modifyUser")
    public String modifyUser(User user) {
        try {
            userService.updateById(user);
            return "/home.html";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "/toModify.html";
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(HttpSession session, String newPassword, String oldPassword) {
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
        return "redirect:/home.html";
    }

}
