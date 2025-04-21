package com.language.service.domain.entities;

import com.language.service.domain.dtos.PermissionCodeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="function_catalog")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getAllPermCode",
                query = "select distinct p.function_code as code \n" +
                        "from tbl_user u\n" +
                        "         inner join user_group_map ugm on u.id = ugm.user_id\n" +
                        "         inner join tbl_group gr on gr.id = ugm.group_id\n" +
                        "         inner join group_function gpm on ugm.group_id = gpm.group_id and gpm.STATUS = 1\n" +
                        "         inner join function_catalog p on gpm.FUNCTION_ID = p.id " +
                        "where u.id = :userId " +
                        "and u.deleted = :deleted " +
                        "and ugm.deleted = :deleted " +
                        "and gpm.deleted = :deleted " +
                        "and gr.deleted = :deleted " +
                        "and p.deleted = :deleted ",
                resultSetMapping = "PermissionCodeDTOMapping"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "PermissionCodeDTOMapping",
                classes = @ConstructorResult(
                        targetClass = PermissionCodeDTO.class,
                        columns = {
                                @ColumnResult(name = "code", type = String.class)
                        }
                )
        )
})
public class FunctionCatalog extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="ID")
   private Long id;

   @Column(name="PARENT_ID")
   private Long parentId;

   @Column(name="FUNCTION_CODE")
   private String functionCode;

   @Column(name="FUNCTION_NAME")
   private String functionName;

   @Column(name="DESCRIPTION")
   private String description;

   @Column(name="STATUS")
   private Integer status;

}
