package com.shoejs.otllo.api.post;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private User user;

    private PostService postService;

    private final UUID id = UUID.fromString("d1db8bd9-c78a-475f-972b-c413dbbee624");

    @BeforeEach
    void setUp() {
        user = User.builder().username("#willow").build();
        postService = new PostService(postRepository);
    }

    @Test
    void testCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(buildPostForTest("title", "body text", user));

        PostDetailsDto postDetails = postService.createPost(new PostCreateUpdateDto("title", "body text"), user);

        assertPostDetailsDto(postDetails, "title", "body text");
    }

    @Test
    void testUpdatePost() {
        when(postRepository.findById(id)).thenReturn(Optional.of(buildPostForTest("original title", "original body text",user)));
        when(postRepository.save(any(Post.class))).thenReturn(buildPostForTest("updated title", "updated body text", user));

        PostDetailsDto postDetails = postService.updatePost(id, new PostCreateUpdateDto("updated title", "updated body text"), user);

        assertPostDetailsDto(postDetails, "updated title", "updated body text");
    }

    @Test
    void testUpdatePostThrowsNotFoundException() {
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.updatePost(id, new PostCreateUpdateDto("updated title", "updated body text"), user))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Post with ID: [%s] not found".formatted(id));
    }

    @Test
    void testUpdatePostThrowsInvalidRequestException() {
        User author = User.builder().id(UUID.randomUUID()).username("#whicas").build();
        when(postRepository.findById(id)).thenReturn(Optional.of(buildPostForTest("title", "Wrong user", author)));

        assertThatThrownBy(() -> postService.updatePost(id, new PostCreateUpdateDto("title", "body"), user))
                .isInstanceOf(InvalidRequestException.class).hasMessage("Only the author can update a post");
    }

    @Test
    void testGetAllPosts() {
        when(postRepository.findAll(ArgumentMatchers.<Specification<Post>>any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(buildPostForTest("all posts", "some text for the body", user))));

        CollectionDetailsDto<PostDetailsDto> collectionDetailsDto = postService.getAllPosts("all posts", 0, 25);

        assertThat(collectionDetailsDto).isNotNull();
        assertThat(collectionDetailsDto.details().size()).isEqualTo(1);
        assertPostDetailsDto(collectionDetailsDto.details().get(0), "all posts", "some text for the body");
        assertThat(collectionDetailsDto.currentPage()).isEqualTo(0);
        assertThat(collectionDetailsDto.totalPages()).isEqualTo(1);
        assertThat(collectionDetailsDto.totalElements()).isEqualTo(1L);
    }

    @Test
    void testGetPostById() {
        when(postRepository.findById(id)).thenReturn(Optional.of(buildPostForTest("get by ID title", "get by ID body", user)));

        PostDetailsDto postDetails = postService.getPostById(id);

        assertPostDetailsDto(postDetails, "get by ID title", "get by ID body");
    }

    @Test
    void testGetPostByIdThrowsNotFoundException() {
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.getPostById(id))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Post with ID: [%s] not found".formatted(id));
    }

    @Test
    void testDeletePostByIdReturnsTrue() {
        when(postRepository.existsById(id)).thenReturn(true);
        when(postRepository.findById(id)).thenReturn(Optional.of(buildPostForTest("delete post", "delete body", user)));

        assertThat(postService.deletePostById(id, user)).isTrue();
        verify(postRepository, times(1)).delete(any(Post.class));
    }

    @Test
    void testDeletePostByIdReturnsFalse() {
        when(postRepository.existsById(id)).thenReturn(false);

        assertThat(postService.deletePostById(id, user)).isFalse();
    }

    @Test
    void testDeletePostByIdThrowsInvalidRequestException() {
        User author = User.builder().id(UUID.randomUUID()).username("#whicas").build();
        when(postRepository.findById(id)).thenReturn(Optional.of(buildPostForTest("title", "body", author)));
        when(postRepository.existsById(id)).thenReturn(true);

        assertThatThrownBy(() -> postService.deletePostById(id, user))
                .isInstanceOf(InvalidRequestException.class).hasMessage("Only the author can delete a post");
    }

    private void assertPostDetailsDto(PostDetailsDto postDetails, String expectedTitle, String expectedBody) {
        assertThat(postDetails).isNotNull();
        assertThat(postDetails.id()).isEqualTo(UUID.fromString("d1db8bd9-c78a-475f-972b-c413dbbee624"));
        assertThat(postDetails.createdAt()).isEqualTo(LocalDateTime.of(2024, 10, 20, 9, 30));
        assertThat(postDetails.updatedAt()).isEqualTo(LocalDateTime.of(2024, 10, 27, 10, 53));
        assertThat(postDetails.title()).isEqualTo(expectedTitle);
        assertThat(postDetails.body()).isEqualTo(expectedBody);
        assertThat(postDetails.author()).isEqualTo("#willow");
    }

    private Post buildPostForTest(String title, String body, User user) {
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 20, 9, 30);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 10, 27, 10, 53);

        return Post.builder().id(id).createdAt(createdAt).updatedAt(updatedAt).title(title)
                .body(body).user(user).build();
    }
}
