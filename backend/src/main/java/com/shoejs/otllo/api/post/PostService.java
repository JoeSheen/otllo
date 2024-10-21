package com.shoejs.otllo.api.post;

import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper mapper = PostMapper.INST;

    public PostDetailsDto createPost(PostCreateUpdateDto createPostDto, User user) {
        Post post = mapper.postCreateUpdateDtoToPost(createPostDto);
        post.setUser(user);
        return mapper.postToPostDetailsDto(postRepository.save(post));
    }

    public PostDetailsDto updatePost(UUID id, PostCreateUpdateDto updatePostDto, User user) {
        Post post = findPostOrThrow(id);
        if (!post.getUser().equals(user)) {
            throw new InvalidRequestException("Only the author can update a post");
        }
        mapper.updatePostFromDto(post, updatePostDto);
        return mapper.postToPostDetailsDto(postRepository.save(post));
    }

    public PostDetailsDto getPostById(UUID id) {
        Post post = findPostOrThrow(id);
        return mapper.postToPostDetailsDto(post);
    }

    public boolean deletePostById(UUID id, User user) {
        boolean deleted = false;
        if (postRepository.existsById(id)) {
            Post post = findPostOrThrow(id);
            if (!post.getUser().equals(user)) {
                throw new InvalidRequestException("Only the author can delete a post");
            }
            postRepository.delete(post);
            deleted = true;
        }
        return deleted;
    }

    private Post findPostOrThrow(UUID id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post with ID: [%s] not found".formatted(id)));
    }
}
