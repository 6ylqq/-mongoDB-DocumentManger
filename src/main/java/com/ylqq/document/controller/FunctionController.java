package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.service.FunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ylqq
 */
@RestController
public class FunctionController {
    @Autowired
    private FunctionRepository functionRepository;

    @RequestMapping("allFunc")
    public List<Function> Allfunc(){
        return functionRepository.findAll();
    }

    @RequestMapping("addFunction")
    public String addFunction(Function function){
        if (!functionRepository.findAllById(function.getFunId())){
            functionRepository.insert(function);
            return "forward:/fun/funList";
        }else {
            return "添加失败！";
        }
    }

    @RequestMapping("removeFunction")
    public String removeFunc(Function function){
        if (!functionRepository.findAllById(function.getFunId())){
            functionRepository.delete(function);
            return "forward:/fun/funList";
        }else {
            return "删除失败！";
        }
    }

}
