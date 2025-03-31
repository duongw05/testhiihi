package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupUserManagementDTO {
    private Long id;
    @NotBlank(message = "ERROR.GROUP_USER.CODE_NOT_NULL")
    @Size(max = 250, message = "ERROR.GROUP_USER.CODE_MAX_LENGTH")
    private String code;
    @NotBlank(message = "ERROR.GROUP_USER.NAME_NOT_NULL")
    @Size(max = 250, message = "ERROR.GROUP_USER.NAME_MAX_LENGTH")
    private String name;
    @Size(max = 500, message = "ERROR.GROUP_USER.DESCRIPTION_MAX_LENGTH")
    private String description;
    private String stt;

}
