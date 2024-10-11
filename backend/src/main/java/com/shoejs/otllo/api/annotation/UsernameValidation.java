package com.shoejs.otllo.api.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to ensure that usernames fulfil the criteria needed to be deemed valid.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface UsernameValidation {

    String message() default "{com.shoejs.otllo.api.annotation.UsernameValidation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
