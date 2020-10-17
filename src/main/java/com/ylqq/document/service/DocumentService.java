package com.ylqq.document.service;

import com.ylqq.document.pojo.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ylqq
 */
public interface DocumentService {

    /**
     * 插入一条公文信息（公文撰稿时调用）
     *
     * @param record
     * @return
     */
    int insertSelective(Document record);

    /**
     * 修改一条公文信息
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Document record);

    /**
     * 模糊查询全部
     *
     * @param article
     * @return
     */
    List<Document> selectListAll(Document article);

    /**
     * 查与我有关的信息
     *
     * @param userid
     * @return
     */
    List<Document> selectMyReceiveList(Integer userid);

    /**
     * 查询审核结果中正在审核的公文
     *
     * @param article
     * @return
     */
    List<Document> selectMyAuditList(Document article);

    /**
     * 查与我有关的信息
     *
     * @param userid
     * @return
     */
    List<Document> selectMyList(Integer userid);

    /**
     * 按id查询公文详细信息
     *
     * @param articleid
     * @return
     */
    Document selectOne(@Param("articleid") Integer articleid,
                       @Param("userId") Integer userId);


    /**
     * 查询等待审核通过公文的数量
     *
     * @param userId 用户id
     * @return
     */
    Long selectMyWaitingCount(Integer userId);

    /**
     * 查询被驳回的公文数量
     *
     * @param userId 用户id
     * @return
     */
    Long selectMyFailCount(Integer userId);

    /**
     * 查询需要我审核的公文数量
     *
     * @param userId 用户id
     * @return
     */
    Long selectMyDealCount(Integer userId);

    /**
     * 查询待收取公文数量
     *
     * @param userId 用户id
     * @return
     */
    Long selectMyCountReceiver(Integer userId);

    /**
     * 撰稿人删除公文
     *
     * @param articleid
     * @return
     */
    Integer deleteById(Integer articleid);

    /**
     * 检查公文标题是否重复
     *
     * @param title
     * @return
     */
    Long validateTitle(@Param("title") String title);
}
