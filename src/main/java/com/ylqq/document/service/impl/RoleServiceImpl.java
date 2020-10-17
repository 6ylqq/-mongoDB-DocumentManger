package com.ylqq.document.service.impl;


import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.service.RoleService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author ylqq
 */
@Service
public class RoleServiceImpl implements RoleService {

    final static String URI = "mongodb://mongos0.example.com:27017,mongos1.example.com:27017:27017/admin";
    final MongoClient client = MongoClients.create(URI);
    /*TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.LOCAL)
            .writeConcern(WriteConcern.MAJORITY)
            .build();*/

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加角色（选择性）
     *
     * @param record 需要添加的记录
     * @return 0和1
     */
    @Override
    public int insertSelective(Role record) {
        try {
            mongoTemplate.insert(record, "role");
            return 1;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    /**
     * 按主键查询（不级联）
     *
     * @param roleid
     * @return
     */
    @Override
    public Role selectByPrimaryKey(Integer roleid) {
        try {
            Query query = Query.query(Criteria.where("roleId").is(roleid));
            return mongoTemplate.findOne(query, Role.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否有相同名称的角色
     *
     * @param roleid
     * @param rolename
     * @return
     */
    @Override
    public List<Role> hasSameRole(Integer roleid, String rolename) {
        try {
            Query query = Query.query(Criteria.where("roleid").is(roleid));
            return mongoTemplate.find(query, Role.class, "role");
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * 按主键选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Role record) {
        Query query = Query.query(Criteria.where("roleid").is(record.getRoleId()));
        if (mongoTemplate.findAndReplace(query, record, "roleright") == null) {
            //找不到
            return 0;
        } else {
            //找到了
            return 1;
        }

    }

    /**
     * 删除某个角色的全部权限信息（更新权限设置用）
     *
     * @param roleid
     * @return
     */
    @Override
    public int deleteOldRoleRights(Integer roleid) {
        try {
            Query query = Query.query(Criteria.where("roleid").is(roleid));
            mongoTemplate.remove(query, "roleright");
            return 1;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }

    }

    /**
     * 更新权限
     *
     * @param roleid 角色id
     * @param funids 功能id列表
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleright(Integer roleid, Integer[] funids) {

        //使用mongoCollection绑定集合
        MongoCollection<Document> mongoCollection = mongoTemplate.getCollection("roleright");

        //这里注意，List要用Document作为实体对象，然后将数据实体化插入
        List<Document> roleRights = null;
        for (Integer funid : funids) {
            assert false;
            //注意要作为一个整体存入list中
            Document document = new Document("roleid", roleid).append("funid", funid);
            roleRights.add(document);
        }

        //使用clientSession开启mongodb的事务管理，进行权限的统一删除，统一增加，以此来达到修改的目的！
        ClientSession clientSession = client.startSession();
        try {
            clientSession.startTransaction();
            //删除指定的roleid角色权限
            mongoCollection.deleteMany(clientSession, Filters.eq("roleid", roleid));
            assert false;
            mongoCollection.insertMany(clientSession, roleRights);
        } catch (Exception e) {
            //回滚
            clientSession.abortTransaction();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        //提交
        clientSession.commitTransaction();
        return true;
    }

    ;

}
