package com.ylqq.document.controller;

import com.ylqq.document.service.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ylqq
 */
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;


}
