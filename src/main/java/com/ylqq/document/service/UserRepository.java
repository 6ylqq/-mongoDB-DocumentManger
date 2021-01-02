package com.ylqq.document.service;

import com.ylqq.document.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ylqq
 */
public interface UserRepository extends MongoRepository<User, Integer> {

    /**
     * 通过id找user
     *
     * @param userid userid
     * @return
     * */
    Optional<User> findByUserid(Integer userid);


    /**
     * 通过id删除用户
     *
     * @param userid 用户id
     * */
    void deleteByUserid(Integer userid);

    /**
     * 通过loginName查用户
     *
     * @param LoginName 登陆名
     * @return
     */
    User findByLoginName(String LoginName);

    /**
     * 通过userid查找对象
     *
     * @param userid 用户id
     * @return
     * */
    Boolean existsByUserid(Integer userid);

    /**
     * 找相同机构下的员工
     *
     * @param instId 员工所处的机构id
     * @return
     * */
    List<User> findAllByInstIdOrderByUserid(Integer instId);

    /**
     * 返回相同机构下的员工数量
     *
     * @param instId 机构id
     * @return
     * */
    Integer countAllByInstId(Integer instId);
}
