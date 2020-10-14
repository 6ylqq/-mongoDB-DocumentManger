package com.ylqq.document.service;

import com.ylqq.document.pojo.Function;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface FunctionRepository extends MongoRepository<Function,Integer> {
}
