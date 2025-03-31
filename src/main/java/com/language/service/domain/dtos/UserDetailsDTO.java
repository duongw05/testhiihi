package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDTO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private String fullName;

    private String locale;

    private String timezone;

    private Boolean enabled;

    private String createdBy;
    private String createdByFullName;
    private LocalDateTime createdDate;

    private String lastModifiedBy;
    private String lastModifiedByFullName;
    private LocalDateTime lastModifiedDate;

    private String avatarUrl;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(Long id, String username, String email, String phone, String fullName, String locale, String timezone, Boolean enabled, String createdBy, String createdByFullName, LocalDateTime createdDate, String lastModifiedBy, String lastModifiedByFullName, LocalDateTime lastModifiedDate, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.locale = locale;
        this.timezone = timezone;
        this.enabled = enabled;
        this.createdBy = createdBy;
        this.createdByFullName = createdByFullName;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedByFullName = lastModifiedByFullName;
        this.lastModifiedDate = lastModifiedDate;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
