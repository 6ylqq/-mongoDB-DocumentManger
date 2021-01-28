package com.ylqq.document.controller;

import com.ylqq.document.pojo.Document;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.DocumentRepository;
import com.ylqq.document.service.InstitutionRepository;
import com.ylqq.document.service.impl.DocumentServiceImpl;
import com.ylqq.document.util.Layui;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ylqq
 */
@Controller
@Api(value = "公文Document相关的控制器")
public class DocumentController {
    private final HttpSession session;

    private final DocumentRepository documentRepository;

    private final InstitutionRepository institutionRepository;

    private final DocumentServiceImpl documentService;

    private static final String USER="user";

    public DocumentController(HttpSession session, DocumentRepository documentRepository, InstitutionRepository institutionRepository, DocumentServiceImpl documentService) {
        this.session = session;
        this.documentRepository = documentRepository;
        this.institutionRepository = institutionRepository;
        this.documentService = documentService;
    }

    @RequestMapping("toAddDoc")
    public String toAddDoc() {
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "doc/addArticle";
        }
    }

    @RequestMapping("todocReceive")
    public String todocReceive() {
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "doc/docReceive";
        }
    }

    @RequestMapping("todocAuditOfMe")
    public String todocOfMe() {
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "doc/docAuditOfMe";
        }
    }

    @RequestMapping("todocWriteByMe")
    public String todocWriteByMe() {
        if (session.getAttribute(USER) == null) {
            return "redirect:toLogin";
        } else {
            return "doc/docWriteByMe";
        }
    }

    @PostMapping("/addDoc")
    @ApiOperation("添加公文")
    public String addDocument(Document document,Model model) {
        //先判断编号是否可用
        //先取到user,表格不能填完所有doc数据
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("msg", "请先登陆系统！");
            return "redirect:/toLogin";
        }
        if (documentRepository.existsByDocumentId(document.getDocumentId())) {
            model.addAttribute("repeat_error", "编号重复");
            return "doc/addArticle";
        } else {
            document.setPublishTime(new Date());
            document.setWriterId(user.getUserid());
            document.setInstId(user.getInstId());
            if (institutionRepository.findByInstId(user.getInstId()).isEmpty()) {
                model.addAttribute("noInst_error", "无此机构！");
                return "doc/addArticle";
            }
            /*设置公文状态为审核中*/
            document.setArticleStatus(0);
            documentRepository.insert(document);
            return "forward:todocWriteByMe";
        }
    }

    @ApiOperation("通过公文id删除公文")
    @RequestMapping("deleteDoc/{docId}")
    @ApiImplicitParam(paramType = "path",dataType = "Integer",name = "docId",value = "公文编号",required = true)
    public String deleteDocument(@PathVariable Integer docId) {
        documentRepository.deleteByDocumentId(docId);
        return "doc/docAuditOfMe";
    }

    @ApiOperation("查询全部公文")
    @RequestMapping("allDoc")
    @ResponseBody
    public Layui findAllDoc() {
        return Layui.data("", (int) documentRepository.count(), documentRepository.findAll());
    }

    @RequestMapping("updateDoc")
    public String updateDoc(Document document) {
        documentService.updateByPrimaryKeySelective(document);
        return "doc/docAuditOfMe";
    }

    @ApiOperation("通过公文id去更新公文")
    @ApiImplicitParam(paramType = "path",dataType = "Integer",name = "docId",value = "公文编号",required = true)
    @RequestMapping("toUpdateDoc/{docId}")
    public String toUpdateDoc(@PathVariable Integer docId) {
        return "/doc/docModify" + docId;
    }

    @RequestMapping("docAuditOfMe")
    @ApiOperation("查询需要我审核的公文")
    @ResponseBody
    public Layui docOfMe() {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Layui.data("用户未登录或登录信息失效", 0, null);
        } else {
            return Layui.data("", documentRepository.countByAuditorId(user.getUserid()), documentRepository.findDocumentsByAuditorIdOrderByPublishTime(user.getUserid()));
        }
    }

    @RequestMapping("docWriteByMe")
    @ResponseBody
    public Layui docWriteByMe() {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Layui.data("用户未登录或登录信息失效", 0, null);
        } else {
            return Layui.data("", documentRepository.countByWriterId(user.getUserid()), documentRepository.findDocumentsByWriterIdOrderByPublishTime(user.getUserid()));
        }
    }

    @RequestMapping("docReceive")
    @ResponseBody
    public Layui docReceive() {
        User user = (User) session.getAttribute("user");
        List<Integer> myRecList=new ArrayList<>();
        myRecList.add(user.getUserid());
        return Layui.data("",
                documentRepository.countAllByReceiverIdsContainsOrderByPublishTime(myRecList),
                documentRepository.findAllByReceiverIdsContainsOrderByPublishTime(myRecList));
    }

}
