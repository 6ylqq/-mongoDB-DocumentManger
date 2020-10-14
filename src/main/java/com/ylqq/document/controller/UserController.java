package com.ylqq.document.controller;

import com.ylqq.document.pojo.User;
import com.ylqq.document.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UserService userService;

    /**
     * 角色Service
     */
    @Autowired
    private RoleService roleService;

    /**
     * 机构service
     */
    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    /**
     * 注册新用户
     * */
    @RequestMapping("/addUser")
    public String addUser(User user){
        try {
            userRepository.insert(user);
            return "forward:/login.html";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "forward:/addUser.html";
        }
    }

    /**
     * 修改个人资料
     * */
    @RequestMapping("/modifyUser")
    public String modifyUser(User user){
        try {
            userService.updateByPrimaryKeySelective(user);
            return "/home.html";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "/toModify.html";
        }
    }

    /**
     * 修改秘密
     * */
    @RequestMapping("/modifyPassword")
    public String modifyPasw(){
        return null;
    }


}
