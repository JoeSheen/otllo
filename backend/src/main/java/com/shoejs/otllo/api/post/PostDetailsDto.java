package com.shoejs.otllo.api.post;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Dto details class for post entity
 *
 * @param id of the post
 * @param createdAt of the post
 * @param updatedAt of the post
 * @param title of the post
 * @param body of the post
 * @param author of the post
 */
public record PostDetailsDto(
        UUID id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String title,
        String body,
        String author
) {}
