package com.shoejs.otllo.api.user;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Dto details class for the user entity
 *
 * @param id of the user
 * @param firstName of the user
 * @param lastName of the user
 * @param dateOfBirth of the user
 * @param gender of the user
 * @param email of the user
 * @param phoneNumber of the user
 * @param username of the user
 * @param profileImage of the user
 * @param visible of the user
 * @param status of the user
 */
public record UserDetailsDto(
        UUID id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String email,
        String phoneNumber,
        String username,
        String profileImage,
        boolean visible,
        String status
) {}
