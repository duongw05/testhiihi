package com.language.service.rest.dto.request.searchparams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupSearchParams {
    private String name;

    private List<Long> idIn;

    private List<Long> idNotIn;

    public List<Long> getIdIn() {
        return idIn;
    }

    public void setIdIn(List<Long> idIn) {
        this.idIn = idIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getIdNotIn() {
        return idNotIn;
    }

    public void setIdNotIn(List<Long> idNotIn) {
        this.idNotIn = idNotIn;
    }
}
