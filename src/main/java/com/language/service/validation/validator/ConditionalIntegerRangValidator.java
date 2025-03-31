package com.language.service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.language.service.validation.constraint.ConditionalIntegerRang;

public class ConditionalIntegerRangValidator implements ConstraintValidator<ConditionalIntegerRang, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(ConditionalIntegerRang constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Assuming null is valid, adjust as needed
        }
        return value >= min && value <= max;
    }

}
