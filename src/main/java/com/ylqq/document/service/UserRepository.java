package com.ylqq.document.service;

import com.ylqq.document.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface UserRepository extends MongoRepository<User, Integer> {
    /**
     * 通过loginName查用户
     *
     * @param LoginName 登陆名
     * @return
     */
    User findByLoginName(String LoginName);
}
