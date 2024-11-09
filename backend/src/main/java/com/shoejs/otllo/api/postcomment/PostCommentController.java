package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post-comment")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentDetailsDto createPostComment(@Valid @RequestBody PostCommentCreateDto createDto, @AuthenticationPrincipal User user) {
        return postCommentService.createPostComment(createDto, user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionDetailsDto<PostCommentDetailsDto> getAllPostComments(@RequestParam UUID searchValue,
            @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        return postCommentService.getAllPostComments(searchValue, pageNumber, pageSize);
    }
}
