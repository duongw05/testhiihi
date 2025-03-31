package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupUserDTO {
    private Long id;
    private Long userId;
    private String name;
    private String description;

    public GroupUserDTO() {
    }

    public GroupUserDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public GroupUserDTO(Long id, Long userId, String name, String description) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
