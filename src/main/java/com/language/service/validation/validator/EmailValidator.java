package com.language.service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.language.service.common.utils.DataUtils;
import com.language.service.validation.constraint.ConditionalEmailPattern;

public class EmailValidator implements ConstraintValidator<ConditionalEmailPattern, String> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Chỉ kiểm tra định dạng nếu email không null hoặc không rỗng
        if (DataUtils.isNullOrEmpty(email)) {
            return true;
        }
        return email.matches(EMAIL_REGEX);
    }
}