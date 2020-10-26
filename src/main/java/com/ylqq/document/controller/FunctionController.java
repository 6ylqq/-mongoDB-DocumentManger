package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.service.FunctionRepository;
import com.ylqq.document.service.RoleRepository;
import com.ylqq.document.util.Layui;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.pattern.PathPattern;

/**
 * @author ylqq
 */
@Controller
public class FunctionController {

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping
    public String toAllFunc(){
        return "/main/resources/templates/sysManager/function/funcList.html";
    }

    @RequestMapping("allFunc")
    public Layui allFunc() {
        return Layui.data("", (int) functionRepository.count(),functionRepository.findAll());
    }

    @RequestMapping("addFunction")
    public String addFunction(Function function, ModelAndView modelAndView) {
        if (functionRepository.findAllByFunId(function.getFunId())) {
            functionRepository.insert(function);
        } else {
            modelAndView.addObject("msg","添加失败");
        }
        return "/main/resources/templates/sysManager/function/funcList.html";
    }

    @RequestMapping("removeFunction")
    public String removeFunc(Function function, Model model) {
        if (functionRepository.findAllByFunId(function.getFunId())) {
            functionRepository.delete(function);
        } else {
            model.addAttribute("error","删除失败");
        }
        return "/main/resources/templates/sysManager/function/funcList.html";
    }

    @RequestMapping("/modifyFunc/{funId}")
    @Synchronized
    public String modifyFunc(String funName, Integer funStatus, @PathVariable Integer funId,Model model){
        if (functionRepository.existsById(funId)){

            functionRepository.deleteById(funId);
            functionRepository.insert(new Function(funId,funName,funStatus));

            //对role进行级联更新!

        }else {
            model.addAttribute("msg","无此权限");
        }
        return "sysManager/function/funcList";
    }
}
