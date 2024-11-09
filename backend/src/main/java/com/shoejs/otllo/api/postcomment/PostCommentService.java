package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.post.PostRepository;
import com.shoejs.otllo.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final PostRepository postRepository;

    private final PostCommentMapper mapper = PostCommentMapper.INST;

    /**
     * Method enabling users to create a new comment
     *
     * @param createDto dto containing data required to create a new post comment
     * @param user creating the post
     * @return {@link PostCommentDetailsDto} based on the provided parameters
     */
    public PostCommentDetailsDto createPostComment(PostCommentCreateDto createDto, User user) {
        Post post = findPostOrThrow(createDto.postId());
        PostComment comment = mapper.postCommentCreateDtoToPostComment(createDto, post, user);
        return mapper.postCommentToPostCommentDetailsDto(postCommentRepository.save(comment));
    }

    /**
     * Method for requesting a collection of post comments for a given post
     *
     * @param postId ID of the post the comments should be associated with
     * @param pageNumber number of the page being requested
     * @param pageSize size of the page being requested
     * @return collection of post comments
     */
    public CollectionDetailsDto<PostCommentDetailsDto> getAllPostComments(UUID postId, int pageNumber, int pageSize) {
        Post post = findPostOrThrow(postId);
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<PostCommentDetailsDto> page = postCommentRepository.findByPost(post, paging).map(mapper::postCommentToPostCommentDetailsDto);
        return new CollectionDetailsDto<>(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    /**
     * Method for deleting a post comment based on its ID
     * @param id of the comment being deleted
     * @param user the user attempting to delete the comment
     * @return true if the post comment was deleted, otherwise false
     */
    public boolean deletePostCommentById(UUID id, User user) {
        boolean deleted = false;
        if (postCommentRepository.existsById(id)) {
            PostComment comment = postCommentRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Post comment with ID: [%s] not found".formatted(id)));
            if (!comment.getUser().equals(user)) {
                throw new InvalidRequestException("Post comment can only be deleted by the author");
            }
            postCommentRepository.delete(comment);
            deleted = true;
        }
        return deleted;
    }

    /**
     * private method for getting a post from {@link PostRepository}
     *
     * @param postId of the post being requested
     * @return the post object matching the ID passed in
     */
    private Post findPostOrThrow(UUID postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post with ID: [%s] not found".formatted(postId)));
    }
}
