package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.language.service.domain.dtos.GroupIdDTO;
import com.language.service.domain.dtos.MenuCodeDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@NamedNativeQueries(
        value = {
                @NamedNativeQuery(
                        name = "getGroupIdsOfUser",
                        query = "select group_id as id from user_group_map where user_id = :userId",
                        resultSetMapping = "GroupIdDTOMapping"
                ),
                @NamedNativeQuery(
                        name = "getMenuCodesByUser",
                        query = "select m.code " +
                                " from user_group_map ugm " +
                                " inner join group_menu_map gmm on ugm.group_id = gmm.group_id " +
                                " inner join menu m on gmm.menu_id = m.id " +
                                " inner join tbl_group gr on gr.id = gmm.group_id " +
                                " where " +
                                " ugm.user_id = :userId " +
                                " and ugm.deleted = :deleted " +
                                " and m.deleted = :deleted " +
                                " and gr.deleted = :deleted " +
                                " and gmm.deleted = :deleted ",
                        resultSetMapping = "MenuCodeDTOMapping"
                )
        }
)

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "MenuCodeDTOMapping",
                        classes = {
                                @ConstructorResult(
                                        targetClass = MenuCodeDTO.class,
                                        columns = {
                                                @ColumnResult(name = "code", type = String.class)
                                        }
                                )
                        }
                ),
                @SqlResultSetMapping(
                        name = "GroupIdDTOMapping",
                        classes = {
                                @ConstructorResult(
                                        targetClass = GroupIdDTO.class,
                                        columns = {
                                                @ColumnResult(name = "id", type = Long.class)
                                        }
                                )
                        }
                )
        }
)
public class User extends BaseEntity implements Serializable {

    public User() {
        enabled = true;
        locale = "vi";
        timezone = "Asia/Ho_Chi_Minh";
    }

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "GENDER")
    private Integer gender;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "LOCALE")
    private String locale;

    @Column(name = "time_zone")
    private String timezone;

    @Column(name = "ENABLED")
    private boolean enabled;

}
