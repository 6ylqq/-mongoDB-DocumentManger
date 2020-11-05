package com.ylqq.document.service;

import com.ylqq.document.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

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

    /**
     * 找相同机构下的员工
     *
     * @param instId 员工所处的机构id
     * @return
     * */
    List<User> findByInstIdOrderByUserid(Integer instId);
}
