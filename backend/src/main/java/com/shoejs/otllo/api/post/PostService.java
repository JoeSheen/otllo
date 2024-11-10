package com.shoejs.otllo.api.post;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Service for performing actions involving {@link Post} objects
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper mapper = PostMapper.INST;

    /**
     * Method for allowing a user to create a new post
     *
     * @param createPostDto object containing post information
     * @param user user creating the new post
     * @return post details based on the newly created post
     */
    public PostDetailsDto createPost(PostCreateUpdateDto createPostDto, User user) {
        Post post = mapper.postCreateUpdateDtoToPost(createPostDto);
        post.setUser(user);
        return mapper.postToPostDetailsDto(postRepository.save(post));
    }

    /**
     * Method allowing a user to update a post.
     *
     * @param id of the post being updated
     * @param updatePostDto the values to update the post with
     * @param user the user performing the update to the post
     * @return an updated post details object
     */
    public PostDetailsDto updatePost(UUID id, PostCreateUpdateDto updatePostDto, User user) {
        Post post = findPostOrThrow(id);
        if (!post.getUser().equals(user)) {
            throw new InvalidRequestException("Only the author can update a post");
        }
        mapper.updatePostFromDto(post, updatePostDto);
        return mapper.postToPostDetailsDto(postRepository.save(post));
    }

    /**
     * Method for returning a collection of posts that match a given search value
     *
     * @param searchValue the search value the posts must contain
     * @param pageNumber number of the page being requested
     * @param pageSize size of the page being requested
     * @return collection of posts that contain the requested search value
     */
    public CollectionDetailsDto<PostDetailsDto> getAllPosts(String searchValue, int pageNumber, int pageSize) {
        Specification<Post> spec = where(PostSpecification.filterPostsBySearchValue(searchValue.toUpperCase()));
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<PostDetailsDto> page = postRepository.findAll(spec, paging).map(mapper::postToPostDetailsDto);
        return new CollectionDetailsDto<>(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    /**
     * Method for getting the post associated with the passed in id
     *
     * @param id of the post being requested
     * @return post details for the post requested
     */
    public PostDetailsDto getPostById(UUID id) {
        Post post = findPostOrThrow(id);
        return mapper.postToPostDetailsDto(post);
    }

    /**
     * Method for deleting a post via its id
     *
     * @param id for the post being deleted
     * @param user the user performing the delete action
     * @return true if the post was deleted, otherwise return false
     */
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

    /**
     * Private method for getting a post from the database
     *
     * @param id of the post being requested from the database
     * @return the post associated with the id parameter
     */
    private Post findPostOrThrow(UUID id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post with ID: [%s] not found".formatted(id)));
    }
}
