package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Setter
@Getter
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;


    @Column(name = "description")
    private String description;

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

}
