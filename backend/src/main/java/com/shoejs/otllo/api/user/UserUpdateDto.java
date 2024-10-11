package com.shoejs.otllo.api.user;

import jakarta.validation.constraints.NotBlank;

/**
 * Dto class for updating user details
 *
 * @param firstName the updated first name for the user
 * @param lastName the updated last name for the user
 * @param email the updated email for the user
 * @param phoneNumber the updated email for the user
 * @param status the updated status for the user
 */
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
