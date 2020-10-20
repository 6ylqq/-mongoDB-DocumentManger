package com.ylqq.document.service;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

/**
 * @author ylqq
 */
public interface RoleRepository extends MongoRepository<Role, Integer> {
}
