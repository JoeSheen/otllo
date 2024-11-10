package com.shoejs.otllo.api.postcomment;

import java.util.UUID;

/**
 * Dto details class for post comment entities
 *
 * @param id of the post comment
 * @param comment content of the post comment
 * @param author of the post comment
 */
public record PostCommentDetailsDto(
        UUID id,
        String comment,
        String author
) {}
