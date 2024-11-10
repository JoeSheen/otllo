package com.shoejs.otllo.api.postcomment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Dto record class used to create new post comments
 *
 * @param comment actual comment value
 * @param postId of the post object the comment is for
 */
public record PostCommentCreateDto(
        @NotBlank
        String comment,
        @NotNull
        UUID postId
) {}
