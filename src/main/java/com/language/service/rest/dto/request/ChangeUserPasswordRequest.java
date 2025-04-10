package com.language.service.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeUserPasswordRequest {
    @NotNull(message = "ERROR.USER.ID_REQUIRE")
    private Long id;

    @NotBlank(message = "ERROR.USER.PASSWORD_NOT_NULL")
    @Size(min = 6, max = 30, message = "ERROR.USER.PASSWORD_LENGTH")
    private String passwordOld;

    @NotBlank(message = "ERROR.USER.PASSWORD_NOT_NULL")
    @Size(min = 6, max = 30, message = "ERROR.USER.PASSWORD_LENGTH")
    private String password;

}
