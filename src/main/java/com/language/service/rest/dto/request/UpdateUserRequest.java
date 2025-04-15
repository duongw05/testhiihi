package com.language.service.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.validation.constraint.ConditionalEmailPattern;
import com.language.service.validation.constraint.ConditionalPhonePattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class UpdateUserRequest {
    Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private Integer gender;
    private MultipartFile avatar;
    private boolean enabled;
}
