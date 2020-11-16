package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.service.FunctionRepository;
import com.ylqq.document.service.RoleRepository;
import com.ylqq.document.service.impl.RoleServiceImpl;
import com.ylqq.document.util.Layui;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ylqq
 */
@Controller
public class FunctionController {

    @Autowired
    private HttpSession session;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleServiceImpl roleService;

    private static final String USER="user";

    @RequestMapping("toAllFunc")
    public String toAllFunc() {
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "sysManager/function/funcList";
        }
    }

    @RequestMapping("allFunc")
    @ResponseBody
    public Layui allFunc() {
        return Layui.data("", (int) functionRepository.count(), functionRepository.findAll());
    }

    @RequestMapping("addFunction")
    public String addFunction(Function function, Model model) {
        if (!functionRepository.existsByFunId(function.getFunId())) {
            functionRepository.insert(function);
        } else {
            model.addAttribute("msg", "添加失败");
        }
        return "forward:toAllFunc";
    }

    @RequestMapping("removeFunction")
    public String removeFunc(Function function, Model model) {
        if (functionRepository.existsByFunId(function.getFunId())) {
            functionRepository.delete(function);
        } else {
            model.addAttribute("error", "删除失败");
        }
        return "sysManager/function/funcList";
    }

    @RequestMapping("/modifyFunc/{funId}")
    @Synchronized
    public String modifyFunc(String funName, Integer funStatus, @PathVariable Integer funId, Model model) {
        if (functionRepository.existsByFunId(funId)) {
            List<Role> roles = roleRepository.findByFunctionsContains(functionRepository.findByFunId(funId));
            for (Role role : roles) {
                //遍历每个角色的每个func，并替换role的内容，然后重新存入到数据库
                //方法虽傻，但管用
                for (Function roleFunction : role.getFunctions()) {
                    if (roleFunction.getFunId().equals(funId)) {
                        roleFunction.setFunName(funName);
                        roleFunction.setFunStatus(funStatus);
                    }
                }
                roleService.updateByPrimaryKeySelective(role);
            }
            functionRepository.deleteByFunId(funId);
            functionRepository.insert(new Function(funId, funName, funStatus));
        } else {
            model.addAttribute("msg", "无此权限");
        }
        return "sysManager/function/funcList";
    }
}
