package com.ylqq.document.service;

import com.ylqq.document.pojo.Function;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author ylqq
 */
public interface FunctionRepository extends MongoRepository<Function, Integer> {
    /**
     * 查重
     *
     * @param funId 功能id
     * @return 是否存在相同id
     * */
    boolean findAllByFunId(Integer funId);
}
