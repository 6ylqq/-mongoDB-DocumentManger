package com.ylqq.document.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author ylqq
 */
@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collation = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String roleName;
    private String roleDescription;
    private List<Function> functions;
}
