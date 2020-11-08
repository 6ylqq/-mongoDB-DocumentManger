package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.User;
import com.ylqq.document.service.UserService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        Query query = Query.query(Criteria.where("loginName").is(loginName).and("userId").is(userid));
        return mongoTemplate.find(query, User.class);
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
        Query query = Query.query(Criteria.where("loginName").is(loginName).and("password").is(password));
        return (User) mongoTemplate.find(query, User.class, "user");
    }

    /**
     * 按主键查找
     *
     * @param userid
     * @return
     */
    @Override
    public User selectByPrimaryKey(Integer userid) {
        Query query = Query.query(Criteria.where("userId").is(userid));
        return mongoTemplate.findOne(query, User.class);
    }

    /**
     * 插入记录
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(User record) {
        mongoTemplate.insert(record, "user");
        return 1;
    }

    /**
     * 修改密码
     *
     * @param update 修改的函数
     * @return
     */
    @Override
    public boolean updatePassword(Update update, Query query) {
        mongoTemplate.updateFirst(query, update, "user");
        return true;
    }

    /**
     * 更新用户资料，除密码外的
     *
     * @param user 新的用户数据
     * @return 修改结果
     */
    @Override
    public boolean updateById(User user) {
        //一部分属性不能用户自己修改
        Update update = new Update()
                .set("username", user.getUserName())
                .set("phone", user.getPhone())
                .set("email", user.getEmail());
        Query query = Query.query(Criteria.where("userid").is(user.getUserid()));
        mongoTemplate.updateFirst(query, update, "user");
        return true;
    }
}
