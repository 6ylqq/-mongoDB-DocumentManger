package com.ylqq.document.service;

import com.ylqq.document.pojo.Institution;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author ylqq
 */
public interface InstitutionRepository extends MongoRepository<Institution, Integer> {

    /**
     * 通过instId找inst
     * @param instId id
     * @return 返回机构
     * */
    Optional<Institution> findByInstId(Integer instId);



}
