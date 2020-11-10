package com.ylqq.document.controller;

import com.ylqq.document.pojo.AuditMessage;
import com.ylqq.document.service.AuditMessageRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ylqq
 */
@Controller
@Api(value = "AuditMessageController——审核信息相关的控制器")
public class AuditMessageController {
    @Autowired
    private AuditMessageRepository auditMessageRepository;

    @ApiOperation(value = "根据公文id获取到公文的审核历史列表")
    @ApiImplicitParam(paramType = "path",name = "docId",value = "公文编号",required = true,dataType = "Integer")
    @RequestMapping(value = "/getDetail/{docId}",method = RequestMethod.GET)
    public String getDetail(Model model,@PathVariable Integer docId){
        model.addAttribute("auditList",auditMessageRepository.findByDocumentIdOrderByAuditTime(docId));
        return "doc/docDetail";
    }

    @ApiImplicitParam(paramType = "path",name = "documentId",value = "公文id",required = true,dataType = "Integer")
    @RequestMapping(value = "/addAudit/{documentId}",method = RequestMethod.POST)
    public String addAudit(@PathVariable Integer documentId, Model model, AuditMessage auditMessage){
        auditMessage.setDocumentId(documentId);
        if (auditMessageRepository.existsByAuditId(auditMessage.getAuditId())){
            model.addAttribute("msg","审核信息id重复！");
        }else {
            auditMessageRepository.insert(auditMessage);
        }
        return "doc/docDetail";
    }
}
