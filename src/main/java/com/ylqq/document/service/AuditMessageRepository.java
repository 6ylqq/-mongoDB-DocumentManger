package com.ylqq.document.service;

import com.ylqq.document.pojo.AuditMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface AuditMessageRepository extends MongoRepository<AuditMessage, Integer> {
}
