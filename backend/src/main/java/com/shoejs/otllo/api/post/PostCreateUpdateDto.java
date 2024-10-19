package com.shoejs.otllo.api.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateUpdateDto(
        @NotBlank
        String title,
        @NotBlank
        @Size(max = 1000)
        String body
) {}
