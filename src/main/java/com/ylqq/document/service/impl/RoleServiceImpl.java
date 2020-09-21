package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.Role;
import com.ylqq.document.pojo.mappingTable.RoleRight;
import com.ylqq.document.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
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
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Role record) {
        if (mongoTemplate.insert(record,"role")==null){
            return 0;
        }else {
            return 1;
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
        Query query=Query.query(Criteria.where("roleId").is(roleid));
        return mongoTemplate.findOne(query,Role.class);
    }

    /**
     * 按主键查询（级联）
     *
     * @param roleid
     * @return
     */
    @Override
    public Role selectByPrimaryKeyCascade(Integer roleid) {
        return null;
    }

    /**
     * 非级联模糊查询
     *
     * @param role
     * @return
     */
    @Override
    public List<Role> selectByKeyword(Role role) {
        return null;
    }

    /**
     * 级联模糊查询
     *
     * @param role
     * @return
     */
    @Override
    public List<Role> selectByKeywordCascade(Role role) {
        return null;
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
        Query query=Query.query(Criteria.where("roleid").is(roleid));
        return mongoTemplate.find(query,Role.class,"role");
    }

    /**
     * 按主键选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return 0;
    }

    /**
     * 删除某个角色的全部权限信息（更新权限设置用）
     *
     * @param roleid
     * @return
     */
    @Override
    public int deleteOldRoleRights(Integer roleid) {
        Query query=Query.query(Criteria.where("roleid").is(roleid));
        if (mongoTemplate.remove(query,"roleright")==null){
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * 为某个角色设置权限
     * 注意要做重复排查，保证roleright的文档唯一性
     *
     * @param roleid 角色id
     * @param funid  功能id
     * @return
     */
    @Override
    public int insertNewRoleRightInfo(Integer roleid, Integer funid) {
        RoleRight roleRight=new RoleRight(roleid,funid);
        Query query=Query.query(Criteria.where("roleid").is(roleid).and("funid").is(funid));
        if (mongoTemplate.findOne(query,RoleRight.class,"roleright")!=null){
            System.out.println("相关角色已有权限！即权限重复！");
            return 0;
        }else if (mongoTemplate.insert(roleRight,"roleright")==null){
            return 0;
        }else {
            return 1;
        }
    }
}
