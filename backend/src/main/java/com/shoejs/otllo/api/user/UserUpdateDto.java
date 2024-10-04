package com.shoejs.otllo.api.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String email,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String status
) {}
