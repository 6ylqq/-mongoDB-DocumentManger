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
@Document("user")
@ApiModel("用户")
public class User implements Serializable {
    @MongoId
    @ApiModelProperty("用户id")
    private Integer userid;

    @ApiModelProperty("登陆名")
    private String loginName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户电话")
    private String phone;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户所属机构id")
    private Integer instId;

    @ApiModelProperty("用户角色id")
    private Integer roleId;

    @ApiModelProperty("用户状态")
    private Integer userStatus;
}
