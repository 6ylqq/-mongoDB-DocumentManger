package com.ylqq.document.service.impl;


import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author ylqq
 */
@Service
public class RoleServiceImpl implements RoleService {
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
     * @param functions 功能列表
     */
    @Override
    public boolean updateRoleright(Integer roleid, List<Function> functions) {
        Query query=Query.query(Criteria.where("roleid").is(roleid));
        Update update=new Update().set("functions",functions);
        return mongoTemplate.findAndModify(query, update, Role.class, "role") != null;
    }
}
