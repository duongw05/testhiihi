package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.language.service.common.ConstantsErrorCode;

/**
 * Generated at 2020/12/01 18:52:17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionDTO {

    private Long id;
    @Size(max = 100, message = ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_GROUP_MAX_LENGTH)
    private String permissionGroup;
    @NotBlank(message = ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_CODE_NOT_NULL)
    @Size(max = 50, message = ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_CODE_MAX_LENGTH)
    private String code;
    @NotBlank(message = ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_NAME_NOT_NULL)
    @Size(max = 200, message = ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_NAME_MAX_LENGTH)
    private String name;

    public PermissionDTO() {
    }

    public PermissionDTO(Long id, String permissionGroup, String code) {
        this.id = id;
        this.permissionGroup = permissionGroup;
        this.code = code;
    }

    public PermissionDTO(Long id, String permissionGroup, String code, String name) {
        this.id = id;
        this.permissionGroup = permissionGroup;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
