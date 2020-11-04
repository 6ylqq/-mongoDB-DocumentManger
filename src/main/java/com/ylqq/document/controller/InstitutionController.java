package com.ylqq.document.controller;

import com.ylqq.document.pojo.Institution;
import com.ylqq.document.service.InstitutionRepository;
import com.ylqq.document.util.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * @author ylqq
 */
@Controller
public class InstitutionController {

    @Autowired
    private HttpSession session;

    @Autowired
    private InstitutionRepository institutionRepository;

    @RequestMapping("/toAllInst")
    public String toAllInst(){
        if (session.getAttribute("user") == null) {
            return "redirect:toLogin";
        } else {
            return "sysManager/inst/instList";
        }
    }

    @RequestMapping("allInst")
    public Layui allInst() {
        return Layui.data("", (int) institutionRepository.count(),institutionRepository.findAll());
    }

    @RequestMapping("addInst")
    public String addInst(Institution institution, Model model) {
        //记得查重id
        if (institutionRepository.findById(institution.getInstId()).isEmpty()) {
            institutionRepository.insert(institution);
        } else {
            model.addAttribute("error","id重复");
        }
        return "forward:toAllInst";
    }
}
