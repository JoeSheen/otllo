package com.shoejs.otllo.api.post;

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
 * Controller for post entities
 */
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * Endpoint allowing a user to create a new post
     *
     * @param createPostDto object containing post information
     * @param user user creating the post
     * @return post details of the new post object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDetailsDto createPost(@Valid @RequestBody PostCreateUpdateDto createPostDto, @AuthenticationPrincipal User user) {
        return postService.createPost(createPostDto, user);
    }

    /**
     * Endpoint for enabling users to update an existing post
     *
     * @param id of the post being updated
     * @param updatePostDto the new values for the post
     * @param user the user performing the update (note the user must be the post author)
     * @return updated post details object
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailsDto updatePost(@PathVariable("id") UUID id,
            @Valid @RequestBody PostCreateUpdateDto updatePostDto, @AuthenticationPrincipal User user) {
        return postService.updatePost(id, updatePostDto, user);
    }

    /**
     * Endpoint for returning a collection of posts that match a given search value
     *
     * @param searchValue the search value the posts must contain (search is done on author and title)
     * @param pageNumber number of the page being requested (default is 0)
     * @param pageSize size of the page being requested (default is 25)
     * @return collection of posts that contain the requested search value
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionDetailsDto<PostDetailsDto> getAllPosts(@RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "25") Integer pageSize) {
        return postService.getAllPosts(searchValue, pageNumber, pageSize);
    }

    /**
     * Endpoint for getting a post via a passed in id
     * @param id of the post
     * @return post details
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailsDto getPostById(@PathVariable("id") UUID id) {
        return postService.getPostById(id);
    }

    /**
     * Endpoint for deleting a post via its id
     * @param id of the post
     * @param user author of the post (note only the author can delete a post)
     * @return ok response if the post was deleted otherwise returns not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") UUID id, @AuthenticationPrincipal User user) {
        if (postService.deletePostById(id, user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
