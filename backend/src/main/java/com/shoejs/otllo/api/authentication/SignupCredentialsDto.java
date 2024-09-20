package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.annotation.UsernameValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

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
        boolean visible
) {}
