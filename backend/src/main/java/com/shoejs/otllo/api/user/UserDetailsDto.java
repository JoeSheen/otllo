package com.shoejs.otllo.api.user;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

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
        Set<UserDetailsDto> friends,
        boolean visible
) {}
