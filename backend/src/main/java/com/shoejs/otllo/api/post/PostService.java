package com.shoejs.otllo.api.post;

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
            throw new RuntimeException("");
        }
        mapper.updatePostFromDto(post, updatePostDto);
        return mapper.postToPostDetailsDto(postRepository.save(post));
    }

    public PostDetailsDto getPostById(UUID id) {
        Post post = findPostOrThrow(id);
        return mapper.postToPostDetailsDto(post);
    }

    private Post findPostOrThrow(UUID id) {
        return postRepository.findById(id).orElseThrow(() ->
                new RuntimeException(""));
    }
}
