package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for post comment entities
 */
@RestController
@RequestMapping("/api/v1/post-comment")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    /**
     * Endpoint allowing a user to create a new post comment
     *
     * @param createDto object containing comment data
     * @param user creating the post
     * @return {@link PostCommentDetailsDto} based on the dto passed in
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentDetailsDto createPostComment(@Valid @RequestBody PostCommentCreateDto createDto, @AuthenticationPrincipal User user) {
        return postCommentService.createPostComment(createDto, user);
    }

    /**
     * Endpoint for returning a collection of post comments for a particular post
     *
     * @param searchValue ID of the Post the comments should all be for
     * @param pageNumber number of the page being requested
     * @param pageSize size of the page being requested
     * @return collection of post comments
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionDetailsDto<PostCommentDetailsDto> getAllPostComments(@RequestParam UUID searchValue,
            @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        return postCommentService.getAllPostComments(searchValue, pageNumber, pageSize);
    }

    /**
     * Endpoint for deleting a post comment via its id
     *
     * @param id of the post being deleted
     * @param user author of the post comment
     * @return ok response if the post comment is deleted otherwise returns not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostCommentById(@PathVariable("id") UUID id, @AuthenticationPrincipal User user) {
        if (postCommentService.deletePostCommentById(id, user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
