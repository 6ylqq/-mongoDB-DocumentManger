package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.Document;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.DocumentService;
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
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入一条公文信息（公文撰稿时调用）
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Document record) {
        mongoTemplate.insert(record, "document");
        return 1;
    }

    /**
     * 修改一条公文信息
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Document record) {
        Query query = Query.query(Criteria.where("ducid").is(record.getDocumentId()));
        Update update = new Update()
                .set("title", record.getTitle())
                .set("publishtime", record.getPublishTime())
                .set("writerId", record.getWriterId())
                .set("copywriter", record.getCopywriter())
                .set("auditorid", record.getAuditorId())
                .set("auditor", record.getAuditor())
                .set("instid", record.getInstId())
                .set("instutution", record.getInstitution())
                .set("documentstatus", record.getArticleStatus())
                .set("receivers", record.getReceivers());
        mongoTemplate.updateFirst(query, update, "document");
        return 1;
    }

    /**
     * 模糊查询全部
     *
     * @param document
     * @return
     */
    @Override
    public List<Document> selectListAll(Document document) {
        return null;
    }

    /**
     * 查收件人是我的公文信息
     *
     * @param userid 我的id
     * @return
     */
    @Override
    public List<Document> selectMyReceiveList(Integer userid) {
        List<Document> documents = mongoTemplate.findAll(Document.class, "document");
        List<Document> result = null;
        for (Document p : documents) {
            for (User o : p.getReceivers()) {
                if (o.getUserid().equals(userid)) {
                    result.add(p);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 查询审核结果中正在审核的公文
     *
     * @param document
     * @return
     */
    @Override
    public List<Document> selectMyAuditList(Document document) {
        Query query = Query.query(Criteria.where("documentstatus").is(0));
        return mongoTemplate.find(query, Document.class, "document");
    }

    /**
     * 查与我有关的信息
     *
     * @param userid
     * @return
     */
    @Override
    public List<Document> selectMyList(Integer userid) {
        List<Document> documents = mongoTemplate.findAll(Document.class, "document");
        List<Document> result = null;
        for (Document p : documents) {
            if (p.getAuditorId().equals(userid) || p.getWriterId().equals(userid)) {
                result.add(p);
                break;
            }
        }
        return result;
    }

    /**
     * 按id查询公文详细信息
     *
     * @param documentId
     * @param userId
     * @return
     */
    @Override
    public Document selectOne(Integer documentId, Integer userId) {
        Query query = Query.query(Criteria.where("documentId").is(documentId).and("userid").is(userId));
        return mongoTemplate.findOne(query, Document.class, "document");
    }


    /**
     * 查询等待审核通过公文的数量
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Long selectMyWaitingCount(Integer userId) {
        Query query = Query.query(Criteria.where("documentstatus").is(0).and("userid").is(userId));
        return mongoTemplate.count(query, "document");
    }

    /**
     * 查询被驳回的公文数量
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Long selectMyFailCount(Integer userId) {
        Query query = Query.query(Criteria.where("documentstatus").is(2).and("userid").is(userId));
        return mongoTemplate.count(query, "document");
    }

    /**
     * 查询需要我审核的公文数量
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Long selectMyDealCount(Integer userId) {
        Query query = Query.query(Criteria.where("auditorId").is(userId));
        return mongoTemplate.count(query, "document");
    }

    /**
     * 查询待收取公文数量
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Long selectMyCountReceiver(Integer userId) {
        List<Document> documents = mongoTemplate.findAll(Document.class, "document");
        long i = 0;
        for (Document p : documents) {
            for (User o : p.getReceivers()) {
                if (o.getUserid().equals(userId)) {
                    ++i;
                    break;
                }
            }
        }
        return i;
    }

    /**
     * 撰稿人删除公文
     *
     * @param documentid
     * @return
     */
    @Override
    public Integer deleteById(Integer documentid) {
        Query query = Query.query(Criteria.where("documentid").is(documentid));
        mongoTemplate.remove(query, "ducoment");
        return 1;
    }

    /**
     * 检查公文标题是否重复
     *
     * @param title
     * @return 0为有重复的标题，1为无重复标题
     */
    @Override
    public Long validateTitle(String title) {
        Query query = Query.query(Criteria.where("title").is(title));
        if (mongoTemplate.findOne(query, Document.class, "document") == null) {
            return 0L;
        } else {
            return 1L;
        }
    }
}
