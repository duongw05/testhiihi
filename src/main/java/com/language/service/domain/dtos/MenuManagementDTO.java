package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.validation.constraint.ConditionalIntegerRang;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuManagementDTO {

    private Long id;

    @NotBlank(message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.MENU_CODE_NOT_NULL)
    @Size(max = 100, message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.MENU_CODE_MAX_LENGTH)
    private String code;

    @NotNull(message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.ORDER_NULL_OR_ZERO)
    @ConditionalIntegerRang(min = 1, max = 999, message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.ORDER_BETWEEN_MIN_AND_MAX)
    private Integer order;

//    @NotNull(message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.LEVEL_NULL_OR_ZERO)
//    @ConditionalIntegerRang(min = 1, max = 999, message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.LEVEL_BETWEEN_MIN_AND_MAX)
    private Integer level;

    private String path;

    private String component;

    private String icon;

    private String name;

    private String title;

    @Size(max = 100, message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.PARENT_CODE_MAX_LENGTH)
    private String parentCode;

    @Size(max = 500, message = ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.DESCRIPTION_MAX_LENGTH)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Saigon")
    private Date createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastModifiedBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Saigon")
    private Date lastModifiedDate;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<MenuManagementDTO> children;

    private boolean hasChildren;

    public MenuManagementDTO(Long id, String code, Integer order, Integer level, String parentCode, String description, boolean hasChildren) {
        this.id = id;
        this.code = code;
        this.order = order;
        this.level = level;
        this.parentCode = parentCode;
        this.description = description;
        this.hasChildren = hasChildren;
    }

    public MenuManagementDTO(Long id, String code, Integer order, Integer level, String parentCode, String description, String path, String name, String title, String icon, String component, boolean hasChildren) {
        this.id = id;
        this.code = code;
        this.order = order;
        this.level = level;
        this.parentCode = parentCode;
        this.description = description;
        this.path = path;
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.component = component;
        this.hasChildren = hasChildren;
    }

    public MenuManagementDTO() {
    }
}
