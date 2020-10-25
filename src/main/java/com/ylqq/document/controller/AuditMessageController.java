package com.ylqq.document.controller;

import com.ylqq.document.service.AuditMessageRepository;
import com.ylqq.document.util.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ylqq
 */
@Controller
public class AuditMessageController {
    @Autowired
    private AuditMessageRepository auditMessageRepository;

    @RequestMapping("/getDetail/{docId}")
    public String getDetail(Model model,@PathVariable Integer docId){
        model.addAttribute("auditList",auditMessageRepository.findByDocumentIdOrderByAuditTime(docId));
        return "doc/docDetail";
    }
}
