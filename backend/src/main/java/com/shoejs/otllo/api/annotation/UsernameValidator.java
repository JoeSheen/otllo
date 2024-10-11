package com.shoejs.otllo.api.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Class used to perform the actual validation work for {@link UsernameValidation}
 */
public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {

    private static final int MIN_LENGTH = 3;

    private static final String SPACE = " ";

    private static final String PREFIX = "#";

    /**
     * Method used to perform the actual validation checks on usernames
     *
     * @param value username object to validated
     * @param context context in which the constraint is evaluated
     *
     * @return true if the username is valid, otherwise false
     */
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

    /**
     * Private method used to build a constraint violation template with a provided message
     *
     * @param context context in which the constraint violation will be set
     * @param message to be used to build constraint violation
     */
    private void setConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
