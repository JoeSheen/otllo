package com.shoejs.otllo.api.post;

import com.shoejs.otllo.api.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDetailsDto createPost(@Valid @RequestBody PostCreateUpdateDto createPostDto, @AuthenticationPrincipal User user) {
        return postService.createPost(createPostDto, user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailsDto updatePost(@PathVariable("id") UUID id,
            @Valid @RequestBody PostCreateUpdateDto updatePostDto, @AuthenticationPrincipal User user) {
        return postService.updatePost(id, updatePostDto, user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailsDto getPostById(@PathVariable("id") UUID id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") UUID id, @AuthenticationPrincipal User user) {
        if (postService.deletePostById(id, user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
