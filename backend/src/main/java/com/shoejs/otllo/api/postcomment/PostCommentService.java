package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.post.PostRepository;
import com.shoejs.otllo.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final PostRepository postRepository;

    private final PostCommentMapper mapper = PostCommentMapper.INST;

    public PostCommentDetailsDto createPostComment(PostCommentCreateDto createDto, User user) {
        Post post = findPostOrThrow(createDto.postId());
        PostComment comment = mapper.postCommentCreateDtoToPostComment(createDto, post, user);
        return mapper.postCommentToPostCommentDetailsDto(postCommentRepository.save(comment));
    }

    public CollectionDetailsDto<PostCommentDetailsDto> getAllPostComments(UUID postId, int pageNumber, int pageSize) {
        Post post = findPostOrThrow(postId);
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<PostCommentDetailsDto> page = postCommentRepository.findByPost(post, paging).map(mapper::postCommentToPostCommentDetailsDto);
        return new CollectionDetailsDto<>(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    private Post findPostOrThrow(UUID postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post with ID: [%s] not found".formatted(postId)));
    }
}
