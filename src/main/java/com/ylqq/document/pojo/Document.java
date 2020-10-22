package com.ylqq.document.pojo;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 公文
 *
 * @author ylqq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@org.springframework.data.mongodb.core.mapping.Document("document")
public class Document {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 公文id
     * 将标题和撰稿人作复合哈希后存入
     */
    private Integer documentId;


    /**
     * 公文标题
     */
    private String title;

    /**
     * 公文
     */
    private String document;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 撰稿人id
     */
    private Integer writerId;

    /**
     * 存放查询出来的撰稿人信息
     */
    private User copywriter;

    /**
     * 审稿人id
     */
    private Integer auditorId;

    /**
     * 存放查询出来的审稿人信息
     */
    private User auditor;

    /**
     * 发布机构id
     */
    private Integer instId;

    /**
     * 存放级联查询出来的发布机构信息
     */
    private Institution institution;

    /**
     * 公文状态：
     * 0 审核中
     * 1 审核通过
     * 2 审核驳回
     * 3 公文发布
     * 4 公文删除
     */
    private Integer articleStatus;

    /**
     * 审核信息
     * 一个公文可能经过多次驳回和审核
     * 做一个List来存储审核信息
     */
    private List<AuditMessage> auditMessages;

    /**
     * 接收人
     */
    private List<User> receivers;

}
