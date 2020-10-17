package com.ylqq.document.service;

import com.ylqq.document.pojo.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface DocumentRepository extends MongoRepository<Document, Integer> {
}
