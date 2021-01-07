package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.Institution;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.InstitutionService;
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
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 动态插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Institution record) {
        mongoTemplate.insert(record, "institution");
        return 1;
    }

    /**
     * 按主键查询
     *
     * @param instid 机构id
     * @return
     */
    @Override
    public Institution selectByPrimaryKey(Integer instid) {
        Query query = Query.query(Criteria.where("instid").is(instid));
        return mongoTemplate.findOne(query, Institution.class, "institution");
    }

    /**
     * 模糊查询
     *
     * @param institution
     * @return
     */
    @Override
    public List<Institution> selectByKeyWord(Institution institution) {
        return null;
    }

    /**
     * 查重
     *
     * @param instid
     * @param instname
     * @return
     */
    @Override
    public List<Institution> hasSameInstitution(Integer instid, String instname) {
        Query query = Query.query(Criteria.where("instid").is(instid).and("instname").is(instname));
        return mongoTemplate.find(query, Institution.class, "institution");
    }

    /**
     * 查询出下面没有用户的机构
     *
     * @return
     */
    @Override
    public List<Institution> selectInstitutionNoUserUnder() {
        List<User> users = mongoTemplate.findAll(User.class, "user");
        List<Institution> institutionList = mongoTemplate.findAll(Institution.class, "institution");
        List<Institution> result = null;
        for (Institution o : institutionList) {
            for (int i = 0; i < users.size() - 1; i++) {
                if (users.get(i).getInstId().equals(o.getInstId())) {
                    break;
                } else if (i == users.size() - 1) {
                    assert false;
                    result.add(o);
                }
            }

        }
        return result;
    }

    /**
     * 查询出下面有用户的机构（未被合并）
     *
     * @return
     */
    @Override
    public List<Institution> selectInstitutionHasUserUnderAndValid() {
        List<User> users = mongoTemplate.findAll(User.class, "user");
        List<Institution> institutionList = mongoTemplate.findAll(Institution.class, "institution");
        List<Institution> result = null;
        for (Institution o : institutionList) {
            for (User p : users) {
                /*重复问题?*/
                if (p.getInstId().equals(o.getInstId())) {
                    assert false;
                    result.add(o);
                    break;
                }
            }

        }
        return result;
    }

    /**
     * 动态更新即选择更新——取或操作即可
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Institution record) {
        Query query = Query.query(Criteria.where("instid").is(record.getInstId()));
        Update update1 = Update.update("instname", record.getInstName());
        Update update2 = Update.update("instaddress", record.getInstAddress());
        Update update3 = Update.update("inststatus", record.getInstStatus());
        if (mongoTemplate.updateFirst(query, update1, "institution") == null && mongoTemplate.updateFirst(query, update2, "instution") == null) {
            mongoTemplate.updateFirst(query, update3, "instution");
        }
        return 1;
    }

    /**
     * 判断机构下面是否还有用户（机构合并的时候用）
     *
     * @param instid 机构id
     * @return
     */
    @Override
    public long isInstitutionHasUser(Integer instid) {
        Query query = Query.query(Criteria.where("instid").is(instid));
        mongoTemplate.find(query, User.class, "user");
        return 1;
    }
}
