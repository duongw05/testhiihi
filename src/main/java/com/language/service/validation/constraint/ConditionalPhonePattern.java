package com.language.service.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.language.service.validation.validator.PhoneValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalPhonePattern {
    String message() default "{ERROR.USER.PHONE_REGEX}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}