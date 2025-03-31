package com.language.service.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "tbl_group")
@EntityListeners(AuditingEntityListener.class)

@NamedNativeQuery(
        name = "getGroupsOfUser",
        query = "select group_id as id from user_group_map where user_id = :userId",
        resultSetMapping = "GroupIdDTOMapping"
)
public class Group extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_SEQ")
    @SequenceGenerator(name = "GROUP_SEQ", sequenceName = "GROUP_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;


    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<UserGroupMap> userGroupMaps;
    @ManyToMany(cascade = {MERGE, PERSIST})
    @JoinTable(
            name = "group_menu_map",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")}
    )
    private Set<Menu> allowedMenus;

    public void addMenu(Menu m) {
        if (allowedMenus == null) {
            allowedMenus = new HashSet<>();
        }
        allowedMenus.add(m);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Menu> getAllowedMenus() {
        return allowedMenus;
    }

    public void setAllowedMenus(Set<Menu> allowedMenus) {
        this.allowedMenus = allowedMenus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
