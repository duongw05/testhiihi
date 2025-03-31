package com.language.service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import com.language.service.common.utils.DataUtils;
import com.language.service.validation.constraint.ConditionalPhonePattern;

public class PhoneValidator implements ConstraintValidator<ConditionalPhonePattern, String> {

    private static final String PHONE_REGEX = "^(84|0[3|5|7|8|9])+([0-9]{8})\\b";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (DataUtils.isNullOrEmpty(phone)) {
            return true;
        }
        return phone.matches(PHONE_REGEX);
    }
}