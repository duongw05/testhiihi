package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

}
