package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ylqq
 */
@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Function record) {
        mongoTemplate.insert(record, "function");
        return 1;
    }

    /**
     * 按主键值查询
     *
     * @param funid
     * @return
     */
    @Override
    public Function selectByPrimaryKey(Integer funid) {
        Query query = Query.query(Criteria.where("funid").is(funid));
        return mongoTemplate.findOne(query, Function.class, "function");
    }

    /**
     * 按照角色id查询功能
     *
     * @param roleid
     * @return
     */
    @Override
    public List<Function> selectByKeyRoleId(int roleid) {
        return null;
    }

    /**
     * 按功能名称查询（查重）
     *
     * @param function
     * @return
     */
    @Override
    public List<Function> selectByFunname(Function function) {
        Query query = Query.query(Criteria.where("funname").is(function.getFunName()));
        return mongoTemplate.find(query, Function.class, "function");
    }

    /**
     * 选择性按主键更新，不更新的字段保持原状就行
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Function record) {
        Query query = Query.query(Criteria.where("funid").is(record.getFunId()));
        Update update = new Update().set("funname", record.getFunName()).set("funstatus", record.getFunStatus());
        mongoTemplate.updateFirst(query, update, "institution");
        return 1;
    }
}
