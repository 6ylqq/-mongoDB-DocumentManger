package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.User;
import com.ylqq.document.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ylqq
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 检查登录名和用户id是否重复
     *
     * @param userid    用户id
     * @param loginName 登录名
     * @return
     */
    @Override
    public List<User> loginNameValidate(Integer userid, String loginName) {
        Query query=Query.query(Criteria.where("loginName").is(loginName).and("userId").is(userid));
       return mongoTemplate.find(query,User.class);
    }

    /**
     * 登录检查
     *
     * @param loginName 登录名
     * @param password  密码
     * @return 符合用户名密码组合的用户记录
     */
    @Override
    public User loginValidate(String loginName, String password) {
        Query query=Query.query(Criteria.where("loginName").is(loginName).and("password").is(password));
        return (User) mongoTemplate.find(query,User.class,"user");
    }

    /**
     * 按条件模糊查询
     *
     * @param user
     * @return
     */
    @Override
    public List<User> selectByKeyWord(User user) {

        return null;
    }

    /**
     * 按主键查找
     *
     * @param userid
     * @return
     */
    @Override
    public User selectByPrimaryKey(Integer userid) {
        Query query=Query.query(Criteria.where("userId").is(userid));
        return mongoTemplate.findOne(query,User.class);
    }

    /**
     * 动态插入记录
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(User record) {
        if ((mongoTemplate.insert(record,"user")==null) ) {
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * 动态更新记录
     *
     * @param record
     * @return 受影响行数
     */
    @Override
    public int updateByPrimaryKeySelective(User record) {

        return 0;
    }
}
