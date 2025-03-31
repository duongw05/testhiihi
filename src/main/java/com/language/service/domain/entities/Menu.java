package com.language.service.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.language.service.domain.dtos.MenuDTO;

import java.io.Serializable;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ")
    @SequenceGenerator(name = "MENU_SEQ", sequenceName = "MENU_SEQ", allocationSize = 1)
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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public int getHideMenu() {
        return hideMenu;
    }

    public void setHideMenu(int hideMenu) {
        this.hideMenu = hideMenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getActiveRouter() {
        return activeRouter;
    }

    public void setActiveRouter(Integer activeRouter) {
        this.activeRouter = activeRouter;
    }
}
