package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.language.service.domain.dtos.PermissionCodeDTO;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.language.service.domain.dtos.PermissionDTO;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "permission")
@EntityListeners(AuditingEntityListener.class)

@NamedNativeQueries(
        value = {
                @NamedNativeQuery(
                        name = "getNotGrantedPermissions",
                        query = "select p.id, p.permission_group as permissionGroup, p.code " +
                                " from permission p where p.id not in( " +
                                "    select permission_id from group_permission_map where group_id = :groupId )",
                        resultSetMapping = "PermissionDTOMapping"),
                @NamedNativeQuery(
                        name = "getGroupPermissions",
                        query = "select " +
                                " p.id as id, p.permission_group as permissionGroup, p.code as code " +
                                " from group_permission_map gpm " +
                                " inner join permission p on p.id = gpm.permission_id " +
                                " where gpm.group_id = :groupId",
                        resultSetMapping = "GroupPermissionDTOMapping"
                ),
                @NamedNativeQuery(
                        name = "allPermCode",
                        query = "select distinct p.code " +
                                "from tbl_user u " +
                                " inner join user_group_map ugm on u.id = ugm.user_id " +
                                " inner join tbl_group gr on gr.id = ugm.group_id " +
                                " inner join group_permission_map gpm on ugm.group_id = gpm.group_id " +
                                " inner join permission p on gpm.permission_id = p.id " +
                                "where u.id = :userId " +
                                "and u.deleted = :deleted " +
                                "and ugm.deleted = :deleted " +
                                "and gpm.deleted = :deleted " +
                                "and gr.deleted = :deleted " +
                                "and p.deleted = :deleted ",
                        resultSetMapping = "PermissionCodeDTOMapping"
                )
        }
)
@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "PermissionDTOMapping",
                        classes = @ConstructorResult(
                                targetClass = PermissionDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "permissionGroup", type = String.class),
                                        @ColumnResult(name = "code", type = String.class)
                                }
                        )
                ),
                @SqlResultSetMapping(
                        name = "GroupPermissionDTOMapping",
                        classes = @ConstructorResult(
                                targetClass = PermissionDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "permissionGroup", type = String.class),
                                        @ColumnResult(name = "code", type = String.class)
                                }
                        )
                )
        }
)



@ToString
public class Permission extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "permission_group")
    private String permissionGroup;


    @Column(name = "name")
    private String name;

}
