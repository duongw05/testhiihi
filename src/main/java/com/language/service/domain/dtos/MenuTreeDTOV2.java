package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuTreeDTOV2 {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String code;

    @JsonIgnore
    private String parentCode;

    private String name;

    @JsonIgnore
    private String title;

    private String path;

    private String component;

    @JsonIgnore
    private String icon;

    private Integer hideMenu;

    @JsonIgnore
    private Integer level;

    @JsonIgnore
    private Integer order;

    private Boolean alwayShow;

    private List<MenuTreeDTOV2> children = new ArrayList<>();

    public Integer activeRouter;

    private Meta meta;

    public MenuTreeDTOV2(Long id, String code, String parentCode, String name, String title, String path, String component, Integer order, Integer level, Integer hideMenu, String icon, Integer activeRouter) {
        this.id = id;
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.title = title;
        this.path = path == null ? "" : path;
        this.component = component;
        this.order = order;
        this.level = level;
        this.hideMenu = hideMenu;
        this.alwayShow = null;
        this.activeRouter = activeRouter;
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role("SEARCH_USER", "SEARCH_USER"));
        this.meta = new Meta(title, icon, null, true, true);
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Meta {
    private String title;
    private String icon;
    private Boolean cache;
    private Boolean hideClose;
    private Boolean activeBreadcrumb;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Role {
    private String code;
    private String desc;
}
