package com.shoejs.otllo.api.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {

    private static final int MIN_LENGTH = 3;

    private static final String SPACE = " ";

    private static final String PREFIX = "#";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() < MIN_LENGTH) {
            setConstraintViolation(context, "Username is too short");
            return false;
        }
        if (value.contains(SPACE)) {
            setConstraintViolation(context, "Username contains whitespace");
            return false;
        }
        if (!value.startsWith(PREFIX)) {
            setConstraintViolation(context, "Username must start with '#'");
            return false;
        }
        return true;
    }

    private void setConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
