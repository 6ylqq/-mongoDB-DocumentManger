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
     * 自增部分放在controller实现
     * 此时默认生成的record的序号是排好序的
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Function record) {
        if (mongoTemplate.insert(record,"function")!=null){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 按主键值查询
     *
     * @param funid
     * @return
     */
    @Override
    public Function selectByPrimaryKey(Integer funid) {
        Query query=Query.query(Criteria.where("funid").is(funid));
        return mongoTemplate.findOne(query,Function.class,"function");
    }

    /**
     * 模糊查询
     *
     * @param function
     * @return
     */
    @Override
    public List<Function> selectByKeyWord(Function function) {
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
        Query query=Query.query(Criteria.where("funname").is(function.getFunName()));
        return mongoTemplate.find(query,Function.class,"function");
    }

    /**
     * 选择性按主键更新，不更新的字段保持原状就行
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Function record) {
        Query query=Query.query(Criteria.where("funid").is(record.getFunId()));
        Update update1=Update.update("funname",record.getFunName());
        Update update2=Update.update("funstatus",record.getFunStatus());
        if (mongoTemplate.updateFirst(query,update1,"institution")!=null||
                mongoTemplate.updateFirst(query,update2,"instution")!=null){
            return 1;
        }else {
            return 0;
        }
    }
}
