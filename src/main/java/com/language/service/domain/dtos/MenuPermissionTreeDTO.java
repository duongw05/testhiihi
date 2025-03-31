package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class MenuPermissionTreeDTO {
    private Long id;
    private String menuCode;
    private String parentMenuCode;
    private Integer level;
    private Integer order;

    private List<MenuPermissionTreeDTO> menus = new ArrayList<>();

    private List<PermissionDTO> permissions = new ArrayList<>();

    public MenuPermissionTreeDTO(Long id, String menuCode, String parentMenuCode, Integer level, Integer order) {
        this.id = id;
        this.menuCode = menuCode;
        this.parentMenuCode = parentMenuCode;
        this.level = level;
        this.order = order;
    }

    public MenuPermissionTreeDTO() {
    }
}
