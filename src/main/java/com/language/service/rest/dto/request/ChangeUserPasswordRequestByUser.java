package com.language.service.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeUserPasswordRequestByUser {
    @NotNull(message = "ERROR.USER.ID_REQUIRE")
    private Long id;
    @NotBlank(message = "ERROR.USER.PASSWORD_NOT_NULL")
    @Size(min = 6, max = 30, message = "ERROR.USER.PASSWORD_LENGTH")
    private String oldPassword;

    @NotBlank(message = "ERROR.USER.PASSWORD_NOT_NULL")
    @Size(min = 6, max = 30, message = "ERROR.USER.PASSWORD_LENGTH")
    private String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
