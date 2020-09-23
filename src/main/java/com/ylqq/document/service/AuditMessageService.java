package com.ylqq.document.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ylqq.document.pojo.AuditMessage;

/**
 * @author ylqq
 */
public interface AuditMessageService {
    /**
     * 添加一条公文审核记录
     * @param record
     * @return
     */
    int insertSelective(AuditMessage record);
}
