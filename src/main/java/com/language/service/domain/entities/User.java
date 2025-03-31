package com.language.service.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.language.service.domain.dtos.GroupIdDTO;
import com.language.service.domain.dtos.MenuCodeDTO;

import java.io.Serializable;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
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

    @Column(name = "LOCALE")
    private String locale;

    @Column(name = "time_zone")
    private String timezone;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "investigation_code")
    private String investigationCode;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private UploadedFile avatar;

    public String getInvestigationCode() {
        return investigationCode;
    }

    public void setInvestigationCode(String investigationCode) {
        this.investigationCode = investigationCode;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserGroupMap> userGroupMaps;

    public List<UserGroupMap> getUserGroupMaps() {
        return userGroupMaps;
    }

    public void setUserGroupMaps(List<UserGroupMap> userGroupMaps) {
        this.userGroupMaps = userGroupMaps;
    }


    public UploadedFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadedFile avatar) {
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
