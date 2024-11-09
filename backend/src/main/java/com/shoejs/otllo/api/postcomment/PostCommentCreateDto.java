package com.shoejs.otllo.api.postcomment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PostCommentCreateDto(
        @NotBlank
        String comment,
        @NotNull
        UUID postId
) {}
