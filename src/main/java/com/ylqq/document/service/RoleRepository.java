package com.ylqq.document.service;

import com.ylqq.document.pojo.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ylqq
 */
public interface RoleRepository extends MongoRepository<Role, Integer> {
}
