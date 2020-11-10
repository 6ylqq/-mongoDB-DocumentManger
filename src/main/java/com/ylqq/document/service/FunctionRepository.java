package com.ylqq.document.service;

import com.ylqq.document.pojo.Function;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


/**
 * @author ylqq
 */
public interface FunctionRepository extends MongoRepository<Function, Integer> {
    /**
     * 通过funId找对应的功能
     *
     * @param funId 功能id
     * @return
     * */
    Optional<Function> findByFunId(Integer funId);

    /**
     * 通过一群id查一群功能
     *
     *
     * */
    Iterable<Function> findAllByFunId(Iterable<Integer> funIds);


    /**
     * 功能id是否存在
     *
     * @param funId funId
     * @return
     * */
    boolean existsByFunId(Integer funId);

    /**
     * 通过id删除功能
     *
     * @param funId funId
     * */
    void deleteByFunId(Integer funId);
}
