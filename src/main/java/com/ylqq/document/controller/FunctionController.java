package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.service.FunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author ylqq
 */
@Controller
public class FunctionController {

    @Autowired
    private FunctionRepository functionRepository;


    @RequestMapping("allFunc")
    public List<Function> allfunc() {
        return functionRepository.findAll();
    }

    @RequestMapping("addFunction")
    public String addFunction(Function function, ModelAndView modelAndView) {
        if (functionRepository.findAllByFunId(function.getFunId())) {
            functionRepository.insert(function);
            return "forward:/fun/funList";
        } else {
            modelAndView.addObject("msg","添加失败");
            return "添加失败！";
        }
    }

    @RequestMapping("removeFunction")
    public String removeFunc(Function function,ModelAndView modelAndView) {
        if (functionRepository.findAllByFunId(function.getFunId())) {
            functionRepository.delete(function);
            return "forward:/fun/funList";
        } else {
            modelAndView.addObject("error","删除失败");
            return "删除失败！";
        }
    }

}
