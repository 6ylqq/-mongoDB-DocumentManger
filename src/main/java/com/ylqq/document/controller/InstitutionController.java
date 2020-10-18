package com.ylqq.document.controller;

import com.ylqq.document.pojo.Institution;
import com.ylqq.document.service.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ylqq
 */
@RestController
public class InstitutionController {
    @Autowired
    private InstitutionRepository institutionRepository;

    @RequestMapping("allInst")
    public List<Institution> allInst(){
        return institutionRepository.findAll();
    }

    @RequestMapping("addInst")
    public String addInst(Institution institution){
        //记得查重id
        if (institutionRepository.findById(institution.getInstId()).isEmpty()) {
            institutionRepository.insert(institution);
            return "forward:/institution/institutions";
        }else {
            return "id重复！";
        }
    }

}
