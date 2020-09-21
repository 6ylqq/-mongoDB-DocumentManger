package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
     * 自增部分放在controller实现。。。想不到怎么做nosql的自增了
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
        return null;
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
        return null;
    }

    /**
     * 选择性按主键更新，不更新的字段设空就行
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Function record) {
        return 0;
    }
}
