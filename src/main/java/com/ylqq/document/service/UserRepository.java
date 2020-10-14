package com.ylqq.document.service;

import com.ylqq.document.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface UserRepository extends MongoRepository<User,Integer> {
}
