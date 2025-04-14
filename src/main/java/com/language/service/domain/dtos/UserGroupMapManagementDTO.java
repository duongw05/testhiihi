package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserGroupMapManagementDTO {
    @NotNull(message =  "ERROR.GROUP_USER.USER_ID_NOT_NULL")
    private Long userId;
    @NotNull(message = "ERROR.GROUP_USER.GROUP_ID_NOT_NULL")
    private Long groupId;
    private Integer status;
    private Integer deleted;
    private String quickSearch;
    private Long id;


}
