package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDetailsDTO {
    private Long id;
    private String code;
    private String name;
    private String description;

    private String createdBy;
    private String createdByFullName;
    private LocalDateTime createdDate;

    private String lastModifiedBy;
    private String lastModifiedByFullName;
    private LocalDateTime lastModifiedDate;

    public GroupDetailsDTO(
            Long id, String name, String description, String createdById, String createdByFullName, LocalDateTime createdDate,
            String lastModifiedById, String lastModifiedByFullName, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdById;
        this.createdByFullName = createdByFullName;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedById;
        this.lastModifiedByFullName = lastModifiedByFullName;
        this.lastModifiedDate = lastModifiedDate;
    }
    public GroupDetailsDTO(
            Long id, String code, String name, String description, String createdBy,  String lastModifiedBy) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCreatedByFullName() {
        return createdByFullName;
    }

    public void setCreatedByFullName(String createdByFullName) {
        this.createdByFullName = createdByFullName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedByFullName() {
        return lastModifiedByFullName;
    }

    public void setLastModifiedByFullName(String lastModifiedByFullName) {
        this.lastModifiedByFullName = lastModifiedByFullName;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
