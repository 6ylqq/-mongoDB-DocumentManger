package com.ylqq.document.service;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ylqq
 */
public interface RoleRepository extends MongoRepository<Role, Integer> {
    /**
     * 查找包含指定功能的角色
     * @param functions 指定功能
     * @return
     * */
    List<Role> findByFunctionsContains(List<Function> functions);
}
