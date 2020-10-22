package com.ylqq.document.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class Institution implements Serializable {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 机构id
     */
    private Integer instId;

    /**
     * 机构名称
     */
    private String instName;

    /**
     * 机构地址
     */
    private String instAddress;

    /**
     * 机构状态
     */
    private String instStatus;

}
