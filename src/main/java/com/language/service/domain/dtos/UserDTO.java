package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private String fullName;

    private String locale;

    private String timezone;

    private Boolean enabled;

    private String groups;
    private String investigationName;
    private String investigationCode;
    private String investigationType;
    private Long investigationId;
    private String stt;

    List<Long> groupIds;

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }
    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, String phone, String fullName,
                   String locale, String timezone, Boolean enabled,String stt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.locale = locale;
        this.stt = stt;
        this.timezone = timezone;
        this.enabled = enabled;
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

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public String getInvestigationName() {
        return investigationName;
    }

    public void setInvestigationName(String investigationName) {
        this.investigationName = investigationName;
    }

    public String getInvestigationCode() {
        return investigationCode;
    }

    public void setInvestigationCode(String investigationCode) {
        this.investigationCode = investigationCode;
    }

    public String getInvestigationType() {
        return investigationType;
    }

    public void setInvestigationType(String investigationType) {
        this.investigationType = investigationType;
    }

    public Long getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(Long investigationId) {
        this.investigationId = investigationId;
    }
}
