package com.shoejs.otllo.api.postcomment;

import com.shoejs.otllo.api.post.Post;
import com.shoejs.otllo.api.user.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PostCommentMapperTest {

    private final UUID postId = UUID.fromString("ab085590-d647-41c6-a4b7-e6c5bca56864");

    private final Post post = Post.builder().id(postId).build();

    private final UUID userId = UUID.fromString("9dad75f5-684d-4976-ab90-8dff6e6ca483");

    private final User user = User.builder().id(userId).username("#aix101").build();

    @Test
    void testPostCommentCreateDtoToPostComment() {
        PostCommentCreateDto createDto = new PostCommentCreateDto("comment for test post", postId);

        PostComment comment = PostCommentMapper.INST.postCommentCreateDtoToPostComment(createDto, post, user);

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isNull();
        assertThat(comment.getCreatedAt()).isNull();
        assertThat(comment.getUpdatedAt()).isNull();
        assertThat(comment.getComment()).isEqualTo("comment for test post");
        assertThat(comment.getPost().getId()).isEqualTo(UUID.fromString("ab085590-d647-41c6-a4b7-e6c5bca56864"));
        assertThat(comment.getUser().getId()).isEqualTo(UUID.fromString("9dad75f5-684d-4976-ab90-8dff6e6ca483"));
        assertThat(comment.getUser().getUsername()).isEqualTo("#aix101");
    }

    @Test
    void testPostCommentToPostCommentDetailsDto() {
        UUID id = UUID.fromString("e316652b-8b17-4dc2-a55e-381b4519376a");
        PostComment comment = PostComment.builder().id(id).comment("comment for mapper test").user(user).build();

        PostCommentDetailsDto commentDetailsDto = PostCommentMapper.INST.postCommentToPostCommentDetailsDto(comment);

        assertThat(commentDetailsDto).isNotNull();
        assertThat(commentDetailsDto.id()).isEqualTo(UUID.fromString("e316652b-8b17-4dc2-a55e-381b4519376a"));
        assertThat(commentDetailsDto.comment()).isEqualTo("comment for mapper test");
        assertThat(commentDetailsDto.author()).isEqualTo("#aix101");
    }
}
