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
@Document(collation = "function")
public class Function implements Serializable {
    /**
     * 功能id
     */
    private Integer funId;

    /**
     * 功能名称
     */
    private String funName;

    /**
     * 功能状态，0禁用，1可用
     */
    private Integer funStatus;
}
