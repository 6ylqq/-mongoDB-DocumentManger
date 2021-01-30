package com.ylqq.document.controller;

import com.ylqq.document.pojo.AuditMessage;
import com.ylqq.document.service.AuditMessageRepository;
import com.ylqq.document.service.DocumentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @author ylqq
 */
@Controller
@Api(value = "AuditMessageController——审核信息相关的控制器")
public class AuditMessageController {
    private final AuditMessageRepository auditMessageRepository;
    private final DocumentRepository documentRepository;
    private final HttpSession session;
    private static final String USER="user";

    public AuditMessageController(AuditMessageRepository auditMessageRepository, DocumentRepository documentRepository, HttpSession session) {
        this.auditMessageRepository = auditMessageRepository;
        this.documentRepository = documentRepository;
        this.session = session;
    }

    @ApiOperation(value = "进入公文审核页面")
    @RequestMapping(value = "/toDocAudit/{docId}")
    @ApiImplicitParam(paramType = "path",name = "docId",value = "公文id",required = true)
    public String toDocAudit(@PathVariable Integer docId,Model model){
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            model.addAttribute("title",documentRepository.findByDocumentId(docId).get().getTitle());
            model.addAttribute("docId",docId);
            return "doc/docAudit";
        }
    }

    @ApiOperation(value = "根据公文id获取到公文的审核历史列表")
    @ApiImplicitParam(paramType = "path",name = "docId",value = "公文编号",required = true,dataType = "Integer")
    @RequestMapping(value = "/getAuditDetail/{docId}",method = RequestMethod.GET)
    public String getDetail(Model model,@PathVariable Integer docId){
        model.addAttribute("auditList",auditMessageRepository.findByDocumentIdOrderByAuditTime(docId));
        return "docAuditDetail";
    }

    @ApiOperation(value = "添加公文审核信息")
    @ApiImplicitParam(paramType = "path",name = "documentId",value = "公文id",required = true,dataType = "Integer")
    @RequestMapping(value = "/addAudit")
    public String addAudit(Model model, AuditMessage auditMessage){
        if (auditMessageRepository.existsByAuditId(auditMessage.getAuditId())){
            model.addAttribute("msg","审核信息id重复！");
        }else {
            auditMessageRepository.insert(auditMessage);
        }
        return "docAuditDetail";
    }
}
