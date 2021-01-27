package com.ylqq.document.service;

import com.ylqq.document.pojo.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ylqq
 */
public interface DocumentRepository extends MongoRepository<Document, Integer> {
    /**
     * 通过撰稿人id查找我撰写的公文
     *
     * @param writerId 撰稿人id
     * @return 返回公文列表
     */
    List<Document> findDocumentsByWriterIdOrderByPublishTime(Integer writerId);

    /**
     * 通过docid查找是否存在目标公文
     *
     * @param documentId 公文id
     * @return
     * */
    boolean existsByDocumentId(Integer documentId);

    /**
     * 查审稿人需要审核的公文
     *
     * @param auditorId 审稿人id
     * @return 返回的公文
     */
    List<Document> findDocumentsByAuditorIdOrderByPublishTime(Integer auditorId);

    /**
     * 需要审核的公文数量
     *
     * @param auditorId 审核人id
     * @return 公文数
     */
    Integer countByAuditorId(Integer auditorId);

    /**
     * 我写的公文数量
     *
     * @param writerId 撰写公文人的id
     * @return 公文数
     */
    Integer countByWriterId(Integer writerId);

    /**
     * 通过docid找公文
     *
     * @param documentId 公文id
     * @return 返回的公文
     * */
    Optional<Document> findByDocumentId(Integer documentId);


    /**
     * 通过docId删除公文
     *
     * @param documentId docId
     * */
    void deleteByDocumentId(Integer documentId);

}
