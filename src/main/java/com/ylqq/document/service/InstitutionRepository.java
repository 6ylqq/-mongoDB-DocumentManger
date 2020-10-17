package com.ylqq.document.service;

import com.ylqq.document.pojo.Institution;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface InstitutionRepository extends MongoRepository<Institution, Integer> {
}
