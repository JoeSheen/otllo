package com.shoejs.otllo.api.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {

    private static final int MIN_LENGTH = 3;

    private static final String PREFIX = "#";

    private static final String SPACE = " ";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.startsWith(PREFIX) && !value.contains(SPACE) && value.length() >= MIN_LENGTH;
    }
}
