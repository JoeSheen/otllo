package com.shoejs.otllo.api.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dto class for creating or updating post objects
 *
 * @param title of the post
 * @param body of the post
 */
public record PostCreateUpdateDto(
        @NotBlank
        String title,
        @NotBlank
        @Size(max = 1000)
        String body
) {}
