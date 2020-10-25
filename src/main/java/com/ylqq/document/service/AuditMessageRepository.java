package com.ylqq.document.service;

import com.ylqq.document.pojo.AuditMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author ylqq
 */
public interface AuditMessageRepository extends MongoRepository<AuditMessage, Integer> {
    /**
     * 通过docID找他的审批历史
     *
     * @param documentId docId
     * @return 返回审批历史
     * */
     List<AuditMessage> findByDocumentIdOrderByAuditTime(Integer documentId);
}
