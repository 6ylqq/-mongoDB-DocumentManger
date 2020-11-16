package com.ylqq.document.controller;

import com.ylqq.document.pojo.Institution;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.InstitutionRepository;
import com.ylqq.document.service.UserRepository;
import com.ylqq.document.util.Layui;
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
public class InstitutionController {

    @Autowired
    private HttpSession session;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String USER="user";

    @RequestMapping("/toAllInst")
    public String toAllInst(){
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "sysManager/inst/instList";
        }
    }

    @RequestMapping("allInst")
    @ResponseBody
    public Layui allInst() {
        return Layui.data("", (int) institutionRepository.count(),institutionRepository.findAll());
    }

    @RequestMapping("addInst")
    public String addInst(Institution institution, Model model) {
        //记得查重id
        if (institutionRepository.findByInstId(institution.getInstId()).isEmpty()) {
            institutionRepository.insert(institution);
        } else {
            model.addAttribute("error","id重复");
        }
        return "forward:toAllInst";
    }

    @RequestMapping("instUser/{inst}")
    @ResponseBody
    public List<User> instUser(@PathVariable Integer inst){
        return userRepository.findByInstIdOrderByUserid(inst);
    }
}
