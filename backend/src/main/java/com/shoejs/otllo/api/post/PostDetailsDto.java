package com.shoejs.otllo.api.post;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDetailsDto(
        UUID id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String title,
        String body
) {}
