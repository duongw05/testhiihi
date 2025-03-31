package com.language.service.domain.dtos;

public class PermissionCodeDTO {
    private String code;

    public PermissionCodeDTO() {
    }

    public PermissionCodeDTO(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
