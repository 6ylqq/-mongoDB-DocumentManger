package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.service.FunctionRepository;
import com.ylqq.document.util.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ylqq
 */
@Controller
public class FunctionController {

    @Autowired
    private FunctionRepository functionRepository;

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
    public String removeFunc(Function function,ModelAndView modelAndView) {
        if (functionRepository.findAllByFunId(function.getFunId())) {
            functionRepository.delete(function);
        } else {
            modelAndView.addObject("error","删除失败");
        }
        return "/main/resources/templates/sysManager/function/funcList.html";
    }

}
