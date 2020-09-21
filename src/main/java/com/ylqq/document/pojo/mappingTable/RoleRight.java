package com.ylqq.document.pojo.mappingTable;

import lombok.*;

import java.io.Serializable;

/**
 * @author ylqq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RoleRight implements Serializable {
    private Integer role_id;
    private Integer fun_id;
}
