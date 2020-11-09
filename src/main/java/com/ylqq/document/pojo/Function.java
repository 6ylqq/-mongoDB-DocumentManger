package com.ylqq.document.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document("function")
@ApiModel(value = "权限")
public class Function implements Serializable {
    @Field
    @ApiModelProperty("权限id")
    private Integer funId;

    @ApiModelProperty("功能名称")
    private String funName;

    @ApiModelProperty(value = "功能状态",notes = "0禁用，1可用")
    private Integer funStatus;
}
