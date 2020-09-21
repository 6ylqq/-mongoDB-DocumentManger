package com.ylqq.document.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ylqq.document.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ylqq
 */
public interface UserService {
    /**
     * 检查用户名是否重复
     * @param userid 用户id
     * @param loginName 登录名
     * @return
     */
    List<User> loginNameValidate(@Param("userid") Integer userid,
                                 @Param("loginname") String loginName);

    /**
     * 登录检查
     * @param loginName 登录名
     * @param password 密码
     * @return 符合用户名密码组合的用户记录
     */
    User loginValidate(@Param("loginName") String loginName,
                       @Param("password") String password);

    /**
     * 按条件模糊查询
     * @param user
     * @return
     */
    List<User> selectByKeyWord(User user);

    /**
     * 按主键查找
     * @param userid
     * @return
     */
    User selectByPrimaryKey(Integer userid);

    /**
     * 动态插入记录
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 动态更新记录
     * @param record
     * @return 受影响行数
     */
    int updateByPrimaryKeySelective(User record);

}
