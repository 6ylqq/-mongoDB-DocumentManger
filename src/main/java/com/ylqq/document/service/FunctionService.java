package com.ylqq.document.service;

import com.ylqq.document.pojo.Function;

import java.util.List;

/**
 * @author ylqq
 */
public interface FunctionService {
    /**
     * 选择性插入，可以让主键自动生成，不用传入
     *
     * @param record
     * @return
     */
    int insertSelective(Function record);

    /**
     * 按主键值查询
     *
     * @param funid
     * @return
     */
    Function selectByPrimaryKey(Integer funid);

    /**
     * 模糊查询
     *
     * @param roleid
     * @return
     */
    List<Function> selectByKeyRoleId(int roleid);

    /**
     * 按功能名称查询（查重）
     *
     * @param function
     * @return
     */
    List<Function> selectByFunname(Function function);

    /**
     * 选择性按主键更新，不更新的字段设空就行
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Function record);

}
