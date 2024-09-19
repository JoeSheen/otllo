package com.shoejs.otllo.api.authentication;

import java.time.LocalDate;

public record SignupCredentialsDto(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String email,
        String phoneNumber,
        String username,
        String password,
        String profileImagePath,
        boolean visible
) {}
