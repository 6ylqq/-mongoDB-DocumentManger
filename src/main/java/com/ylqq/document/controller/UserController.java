package com.ylqq.document.controller;

import com.ylqq.document.service.InstitutionService;
import com.ylqq.document.service.RoleService;
import com.ylqq.document.service.UserService;
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


}
