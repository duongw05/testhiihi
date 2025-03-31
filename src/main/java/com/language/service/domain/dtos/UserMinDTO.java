package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMinDTO {
    private long id;
    private String username;
    private String investigationCode;
    private String investigationType;

    public UserMinDTO(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserMinDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
