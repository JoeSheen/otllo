package com.shoejs.otllo.api.post;

import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    void testGetPostById() {
    }

    @Test
    void testGetPostByIdThrowsNotFoundException() {}

    @Test
    void testDeletePostById() {
    }

    @Test
    void testDeletePostByIdNotFoundException() {}

    @Test
    void testDeletePostByIdThrowsInvalidRequestException() {}

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