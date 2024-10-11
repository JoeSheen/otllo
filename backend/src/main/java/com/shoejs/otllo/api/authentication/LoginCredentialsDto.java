package com.shoejs.otllo.api.authentication;

import jakarta.validation.constraints.NotBlank;

/**
 * Dto class used to contain the data needed to authenticate users on login
 *
 * @param username of the user attempting to log in
 * @param password of the user attempting to log in
 */
public record LoginCredentialsDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {}
