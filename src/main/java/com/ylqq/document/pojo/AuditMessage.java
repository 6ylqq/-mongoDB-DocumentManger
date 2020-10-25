package com.ylqq.document.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核意见
 *
 * @author ylqq
 */
@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document("auditMessage")
public class AuditMessage implements Serializable {
    /**
     * 序列化id
     */
    public static final long serialVersionUID = 1L;

    /**
     * 审核意见id
     * 主键
     */
    private Integer auditId;

    /**
     * 公文id
     */
    private Integer documentId;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核意见
     */
    private String auditAdvice;

    /**
     * 审核结果
     */
    private Integer auditResult;
}
