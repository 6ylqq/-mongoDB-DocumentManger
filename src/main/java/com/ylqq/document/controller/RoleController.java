package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.FunctionRepository;
import com.ylqq.document.service.RoleRepository;
import com.ylqq.document.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * @author ylqq
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    /**
     * 角色Service
     */
    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FunctionRepository functionRepository;


    /**
     * 更新权限
     *
     * @param map
     * @param roleid 角色id
     * @param funids 用户选中的权限id
     * @return
     */
    @RequestMapping("/updateRole")
    public String updateRoleRight(Map<String, Object> map, Integer roleid, Integer[] funids) {

        //查出角色信息
        Optional<Role> role = roleRepository.findById(roleid);
        List<Function> functions = (List<Function>) functionRepository.findAllById(Arrays.asList(funids));

        //修改权限列表
        map.put("result", roleService.updateRoleright(roleid, functions));

        if (role.isPresent()) {
            if (roleService.updateRoleright(roleid, functions)) {
                map.put("msg", "修改角色[" + role.get().getRoleName() + "]的权限信息成功！");
            } else {
                map.put("msg", "修改角色[" + role.get().getRoleName() + "]的权限信息失败！");
            }
        }
        return "forward:/role/roles";
    }

    /**
     * 进入个人资料权限页面
     *
     * @return
     */
    @RequestMapping("/toRoleRight")
    public String toRoleRight(Map<String, Object> map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (roleRepository.findById(user.getRoleId()).isPresent()) {
            ArrayList<Function> functions = new ArrayList<>(roleRepository.findById(user.getRoleId()).get().getFunctions());
            map.put("functions", functions);
        }
        return "forward:/home";

    }

    /**
     * 进入修改页面
     *
     * @return
     */
    @RequestMapping("/toModify")
    public String toModify(Map<String, Object> map, Integer roleid) {

        Optional<Role> role = roleRepository.findById(roleid);
        role.ifPresent(value -> map.put("role", value));

        return "role/rolemodify";
    }

    /**
     * 进入添加页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "role/roleadd";
    }

    /**
     * 添加角色
     *
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Map<String, Object> map, @Valid Role role) {
        roleRepository.insert(role);
        return "forward:/role/roles";
    }
}
