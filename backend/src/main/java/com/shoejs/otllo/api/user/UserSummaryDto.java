package com.shoejs.otllo.api.user;

import java.util.UUID;

public record UserSummaryDto(
        UUID id,
        String username,
        String profileImage,
        String firstName,
        String lastName
) {}
