package com.language.service.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.validation.constraint.ConditionalEmailPattern;
import com.language.service.validation.constraint.ConditionalPhonePattern;
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class UpdateUserProfileRequest {
    @NotNull(message = ConstantsErrorCode.USER.ERROR_USER_ID_REQUIRE)
    Long id;

    @NotBlank(message = ConstantsErrorCode.USER.ERROR_USER_USER_NAME_NOT_NULL)
    @Size(min = 5, max = 30, message = ConstantsErrorCode.USER.ERROR_USER_USER_NAME_LENGTH)
    private String username;

    @ConditionalEmailPattern(message = ConstantsErrorCode.USER.ERROR_USER_EMAIL_REGEX)
    @Size(max = 50, message = ConstantsErrorCode.USER.ERROR_USER_EMAIL_LENGTH)
    private String email;

    @ConditionalPhonePattern(message = ConstantsErrorCode.USER.ERROR_USER_PHONE_REGEX)
    private String phone;

    @NotBlank(message = ConstantsErrorCode.USER.ERROR_USER_FULL_NAME_NOT_NULL)
    @Size(max = 100, message = ConstantsErrorCode.USER.ERROR_USER_FULL_NAME_LENGTH)
    private String fullName;

}
