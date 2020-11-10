package com.ylqq.document.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@ApiModel(value = "审核意见")
public class AuditMessage implements Serializable {
    @Field
    @ApiModelProperty("审核意见id")
    private Integer auditId;

    @ApiModelProperty("公文id")
    private Integer documentId;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("审核意见")
    private String auditAdvice;

    @ApiModelProperty("审核结果")
    private Integer auditResult;
}
