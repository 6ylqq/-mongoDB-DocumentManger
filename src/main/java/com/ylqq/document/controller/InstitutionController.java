package com.ylqq.document.controller;

import com.ylqq.document.pojo.Institution;
import com.ylqq.document.service.InstitutionRepository;
import com.ylqq.document.service.UserRepository;
import com.ylqq.document.util.Layui;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @author ylqq
 */
@Controller
public class InstitutionController {

    private static final String USER = "user";
    private final HttpSession session;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;

    public InstitutionController(HttpSession session, InstitutionRepository institutionRepository, UserRepository userRepository) {
        this.session = session;
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/toAllInst")
    public String toAllInst() {
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "sysManager/inst/instList";
        }
    }

    @RequestMapping("allInst")
    @ResponseBody
    public Layui allInst() {
        return Layui.data("", (int) institutionRepository.count(), institutionRepository.findAll());
    }

    @RequestMapping("addInst")
    public String addInst(Institution institution, Model model) {
        //记得查重id
        if (institutionRepository.findByInstId(institution.getInstId()).isEmpty()) {
            institutionRepository.insert(institution);
        } else {
            model.addAttribute("error", "id重复");
        }
        return "forward:toAllInst";
    }

    @RequestMapping("instUser/{inst}")
    @ResponseBody
    public Layui instUser(@PathVariable Integer inst) {
        return Layui.data("", userRepository.countAllByInstId(inst), userRepository.findAllByInstIdOrderByUserid(inst));
    }

    @RequestMapping("/findInst/{instId}")
    @ResponseBody
    public Institution findInst(@PathVariable Integer instId) {
        if (institutionRepository.findByInstId(instId).isPresent()) {
            return institutionRepository.findByInstId(instId).get();
        }
        return null;
    }
}
