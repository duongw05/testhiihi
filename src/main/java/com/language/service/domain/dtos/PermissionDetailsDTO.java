package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Generated
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionDetailsDTO {

    private Integer id;

    private String code;

    public PermissionDetailsDTO() {
    }

    public PermissionDetailsDTO(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
