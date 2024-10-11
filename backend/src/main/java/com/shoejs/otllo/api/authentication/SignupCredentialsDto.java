package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.annotation.UsernameValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Dto class containing the data needed to create and authenticate new users
 *
 * @param firstName the users first name
 * @param lastName the users last name
 * @param dateOfBirth the users date of birth
 * @param gender the users gender
 * @param email the users email
 * @param phoneNumber the users phone number
 * @param username the users username
 * @param password the users password
 * @param profileImagePath the path to the users profile image
 * @param visible boolean indicating if the user is visible
 * @param status the users status
 */
public record SignupCredentialsDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Past
        LocalDate dateOfBirth,
        String gender,
        @Email
        String email,
        String phoneNumber,
        @NotBlank
        @UsernameValidation
        String username,
        @NotBlank
        String password,
        String profileImagePath,
        boolean visible,
        String status
) {}
