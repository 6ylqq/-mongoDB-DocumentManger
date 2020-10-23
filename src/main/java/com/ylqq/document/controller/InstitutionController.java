package com.ylqq.document.controller;

import com.ylqq.document.pojo.Institution;
import com.ylqq.document.service.InstitutionRepository;
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
public class InstitutionController {
    @Autowired
    private InstitutionRepository institutionRepository;

    @RequestMapping("toAllInst")
    public String toAllInst(){
        return "sysManager/inst";
    }

    @RequestMapping("allInst")
    public List<Institution> allInst() {
        return institutionRepository.findAll();
    }

    @RequestMapping("addInst")
    public String addInst(Institution institution, ModelAndView modelAndView) {
        //记得查重id
        if (institutionRepository.findById(institution.getInstId()).isEmpty()) {
            institutionRepository.insert(institution);
            return "forward:/institution/institutions";
        } else {
            modelAndView.addObject("error","id重复");
            return "id重复！";
        }
    }

}
