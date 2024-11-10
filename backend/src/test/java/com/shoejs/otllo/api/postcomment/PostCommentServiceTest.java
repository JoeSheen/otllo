package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.post.PostRepository;
import com.shoejs.otllo.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostCommentServiceTest {

    @Mock
    private PostCommentRepository postCommentRepository;

    @Mock
    private PostRepository postRepository;

    private User user;

    private final UUID id = UUID.fromString("cc9bec32-9298-4df2-9be0-885596b7a800");

    private PostCommentService postCommentService;

    private final UUID postId = UUID.fromString("5f70517b-da82-4e11-8483-c97474cdc534");

    @BeforeEach
    void setUp() {
        user = User.builder().username("#Scorpio99").build();
        postCommentService = new PostCommentService(postCommentRepository, postRepository);
    }

    @Test
    void createPostComment() {
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.of(buildPostForTest()));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(buildPostCommentForTest());

        PostCommentCreateDto createDto = new PostCommentCreateDto("some text to act as content for a comment", postId);
        PostCommentDetailsDto commentDetailsDto = postCommentService.createPostComment(createDto, user);

        assertPostCommentDetailsDto(commentDetailsDto);
    }

    @Test
    void createPostCommentNotFoundException() {
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        PostCommentCreateDto createDto = new PostCommentCreateDto("some text to act as content for a comment", postId);
        assertThatThrownBy((() -> postCommentService.createPostComment(createDto, user)))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Post with ID: [%s] not found".formatted(postId));
    }

    @Test
    void getAllPostComments() {
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.of(buildPostForTest()));
        when(postCommentRepository.findByPost(any(Post.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(buildPostCommentForTest())));

        CollectionDetailsDto<PostCommentDetailsDto> collectionDetailsDto = postCommentService.getAllPostComments(postId, 0, 10);

        assertThat(collectionDetailsDto).isNotNull();
        assertThat(collectionDetailsDto.details().size()).isEqualTo(1);
        assertPostCommentDetailsDto(collectionDetailsDto.details().get(0));
        assertThat(collectionDetailsDto.currentPage()).isEqualTo(0);
        assertThat(collectionDetailsDto.totalPages()).isEqualTo(1);
        assertThat(collectionDetailsDto.totalElements()).isEqualTo(1L);
    }

    @Test
    void getAllPostCommentsThrowsNotFoundException() {
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postCommentService.getAllPostComments(postId, 0, 10))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Post with ID: [%s] not found".formatted(postId));
    }

    @Test
    void deletePostCommentByIdReturnsTrue() {
        when(postCommentRepository.existsById(any(UUID.class))).thenReturn(true);
        when(postCommentRepository.findById(any(UUID.class))).thenReturn(Optional.of(buildPostCommentForTest()));

        assertThat(postCommentService.deletePostCommentById(id, user)).isTrue();
    }

    @Test
    void deletePostCommentByIdReturnsFalse() {
        when(postCommentRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThat(postCommentService.deletePostCommentById(id, user)).isFalse();
    }

    @Test
    void deletePostCommentByThrowsInvalidRequestException() {
        when(postCommentRepository.existsById(any(UUID.class))).thenReturn(true);
        when(postCommentRepository.findById(any(UUID.class))).thenReturn(Optional.of(buildPostCommentForTest()));

        User author = User.builder().username("#N4ch0123").build();
        assertThatThrownBy(() -> postCommentService.deletePostCommentById(id, author))
                .isInstanceOf(InvalidRequestException.class).hasMessage("Post comment can only be deleted by the author");
    }

    private void assertPostCommentDetailsDto(PostCommentDetailsDto commentDetailsDto) {
        assertThat(commentDetailsDto).isNotNull();
        assertThat(commentDetailsDto.id()).isEqualTo(UUID.fromString("cc9bec32-9298-4df2-9be0-885596b7a800"));
        assertThat(commentDetailsDto.comment()).isEqualTo("some text to act as content for a comment");
        assertThat(commentDetailsDto.author()).isEqualTo("#Scorpio99");
    }

    private PostComment buildPostCommentForTest() {
        String comment = "some text to act as content for a comment";
        return PostComment.builder().id(id).comment(comment).user(user).build();
    }

    private Post buildPostForTest() {
        return Post.builder().id(postId).build();
    }
}
