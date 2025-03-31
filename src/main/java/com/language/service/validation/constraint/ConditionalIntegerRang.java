package com.language.service.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.language.service.validation.validator.ConditionalIntegerRangValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConditionalIntegerRangValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalIntegerRang {
    String message() default "Value must be between {min} and {max}";

    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
