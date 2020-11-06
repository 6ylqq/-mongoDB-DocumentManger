package com.ylqq.document.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
@ApiModel(value = "公文")
public class Document {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    @MongoId
    @ApiModelProperty(value = "公文id")
    private Integer documentId;

    @ApiModelProperty("公文标题")
    private String title;

    @ApiModelProperty("公文")
    private String document;

    @ApiModelProperty("发布时间")
    private Date publishTime;

    @ApiModelProperty("撰稿人id")
    private Integer writerId;

    @ApiModelProperty("存放查询出来的撰稿人信息")
    private User copywriter;

    @ApiModelProperty("审稿人id")
    private Integer auditorId;

    @ApiModelProperty("存放查询出来的审稿人信息")
    private User auditor;

    @ApiModelProperty("发布机构id")
    private Integer instId;

    @ApiModelProperty("存放级联查询出来的发布机构信息")
    private Institution institution;

    @ApiModelProperty(value = "公文状态",
            notes = "0 审核中\n" +
            "1 审核通过\n" +
            "2 审核驳回\n" +
            "3 公文发布\n" +
            "4 公文删除")
    private Integer articleStatus;

    @ApiModelProperty("接收人")
    private List<User> receivers;

}
