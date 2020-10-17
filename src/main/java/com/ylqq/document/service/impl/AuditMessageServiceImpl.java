package com.ylqq.document.service.impl;

import com.ylqq.document.pojo.AuditMessage;
import com.ylqq.document.service.AuditMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ylqq
 */
@Service
public class AuditMessageServiceImpl implements AuditMessageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加一条公文审核记录
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(AuditMessage record) {
        if (mongoTemplate.insert(record, "auditmessgae") != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
