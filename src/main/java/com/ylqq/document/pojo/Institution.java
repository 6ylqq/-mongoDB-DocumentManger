package com.ylqq.document.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

/**
 * @author ylqq
 */
@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document("institution")
@ApiModel("机构")
public class Institution implements Serializable {
    @MongoId
    @ApiModelProperty("机构id")
    private Integer instId;

    @ApiModelProperty("机构名称")
    private String instName;

    @ApiModelProperty("机构地址")
    private String instAddress;

    @ApiModelProperty("机构状态")
    private String instStatus;

}
