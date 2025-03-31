package com.language.service.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.validation.constraint.ConditionalEmailPattern;
import com.language.service.validation.constraint.ConditionalPhonePattern;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class CreateUserRequest {

    @NotBlank(message = ConstantsErrorCode.USER.ERROR_USER_USER_NAME_NOT_NULL)
    @Size(min = 5, max = 30, message = ConstantsErrorCode.USER.ERROR_USER_USER_NAME_LENGTH)
    private String username;

    @NotBlank(message = ConstantsErrorCode.USER.ERROR_USER_PASSWORD_NOT_NULL)
    @Size(min = 6, max = 30, message = ConstantsErrorCode.USER.ERROR_USER_PASSWORD_LENGTH)
    private String password;

    @ConditionalEmailPattern(message = ConstantsErrorCode.USER.ERROR_USER_EMAIL_REGEX)
    @Size(max = 50, message = ConstantsErrorCode.USER.ERROR_USER_EMAIL_LENGTH)
    private String email;

    @ConditionalPhonePattern(message = ConstantsErrorCode.USER.ERROR_USER_PHONE_REGEX)
    private String phone;

    @NotBlank(message = ConstantsErrorCode.USER.ERROR_USER_FULL_NAME_NOT_NULL)
    @Size(max = 100, message = ConstantsErrorCode.USER.ERROR_USER_FULL_NAME_LENGTH)
    private String fullName;
    @NotNull(message = ConstantsErrorCode.USER.ERROR_USER_GROUP_NOT_NULL)
    @NotEmpty(message = ConstantsErrorCode.USER.ERROR_USER_GROUP_NOT_NULL)
    private List<Long> groupIds;

    @NotNull(message = ConstantsErrorCode.USER.ERROR_USER_STATUS)
    private boolean enabled;

    @NotNull(message = ConstantsErrorCode.USER.ERROR_USER_INVESTIGATION_ID)
    private Long investigationId;

    @NotNull(message = ConstantsErrorCode.USER.ERROR_USER_INVESTIGATION_CODE)
    @NotEmpty(message = ConstantsErrorCode.USER.ERROR_USER_INVESTIGATION_CODE_MAXLENGTH)
    private String investigationCode;

}
