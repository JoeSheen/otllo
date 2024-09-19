package com.shoejs.otllo.api.authentication;

import jakarta.validation.constraints.NotBlank;

public record LoginCredentialsDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {}
