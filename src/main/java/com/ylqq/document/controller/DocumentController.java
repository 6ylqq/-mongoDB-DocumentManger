package com.ylqq.document.controller;

import com.ylqq.document.pojo.Document;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.DocumentRepository;
import com.ylqq.document.service.InstitutionRepository;
import com.ylqq.document.service.impl.DocumentServiceImpl;
import com.ylqq.document.util.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author ylqq
 */
@Controller
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private DocumentServiceImpl documentService;

    @RequestMapping("toAddDoc")
    public String toAddDoc(){
        return "doc/addArticle";
    }


    @PostMapping("addDoc")
    public String addDocument(Document document, HttpSession httpSession, ModelAndView modelAndView) {
        //先判断编号是否可用
        if (documentRepository.existsById(document.getDocumentId())) {
            modelAndView.addObject("repeat_error","编号重复");
            return "doc/addArticle";
        } else {
            //先取到user,表格不能填完所有doc数据
            User user = (User) httpSession.getAttribute("user");
            if (user==null){
                modelAndView.addObject("msg","请先登陆系统！");
                return "/login";
            }
            document.setPublishTime(new Date());
            document.setWriterId(user.getUserid());
            document.setCopywriter(user);
            document.setInstId(user.getInstId());
            if (institutionRepository.findById(user.getInstId()).isPresent()) {
                document.setInstitution(institutionRepository.findById(user.getInstId()).get());
            } else {
                modelAndView.addObject("noInst_error","无此机构！");
                return "doc/addArticle";
            }
            document.setArticleStatus(0);
            /*
             * 记得要在表格中增加接收者*/

            documentRepository.insert(document);
            return "forward:/allDoc";
        }
    }

    @RequestMapping("deleteDoc")
    public String deleteDocument(Integer docId) {
        documentRepository.deleteById(docId);
        return "/doc/docList";
    }

    @RequestMapping("allDoc")
    public Layui findAllDoc() {
        return Layui.data("",(int) documentRepository.count(), documentRepository.findAll());
    }

    @RequestMapping("updateDoc")
    public String updateDoc(Document document) {
        documentService.updateByPrimaryKeySelective(document);
        return "doc/docList";
    }

    @RequestMapping("toUpdateDoc")
    public String toUpdateDoc() {
        return "doc/docUpdate";
    }

}
