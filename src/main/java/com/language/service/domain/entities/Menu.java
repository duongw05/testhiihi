package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.language.service.domain.dtos.MenuDTO;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "menu")
@EntityListeners(AuditingEntityListener.class)

@NamedNativeQuery(
        name = "getNotGrantedMenus",
        query = "select m.id, m.code, m.`order`, m.level, " +
                " m.parent_code as parentCode, m.description " +
                " from menu m where m.id not in" +
                " ( select menu_id from group_menu_map where group_id = :groupId )",
        resultSetMapping = "MenuDTOMapping"
)
@SqlResultSetMapping(
        name = "MenuDTOMapping",
        classes = @ConstructorResult(
                targetClass = MenuDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "order", type = Integer.class),
                        @ColumnResult(name = "level", type = Integer.class),
                        @ColumnResult(name = "parentCode", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                }
        )
)
public class Menu extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "order_column")
    private int order;

    @Column(name = "LEVEL_COLUMN")
    private int level;

    @Column(name = "parent_code")
    private String parentCode;

    @Column(name = "description")
    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "component")
    private String component;

    @Column(name = "hide_menu")
    private int hideMenu;

    @Column(name = "icon")
    private String icon;

    @Column(name = "active_router")
    private Integer activeRouter;

}
