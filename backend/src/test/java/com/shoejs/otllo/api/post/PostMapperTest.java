package com.shoejs.otllo.api.post;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PostMapperTest {

    @Test
    void testPostCreateUpdateDtoToPost() {
        PostCreateUpdateDto createDto = new PostCreateUpdateDto("New Post", "Some content for this post!");

        Post post = PostMapper.INST.postCreateUpdateDtoToPost(createDto);

        assertThat(post).isNotNull();
        assertThat(post.getId()).isNull();
        assertThat(post.getCreatedAt()).isNull();
        assertThat(post.getUpdatedAt()).isNull();
        assertThat(post.getTitle()).isEqualTo("New Post");
        assertThat(post.getBody()).isEqualTo("Some content for this post!");
        assertThat(post.getUser()).isNull();
    }

    @Test
    void postToPostDetailsDto() {
        Post post = createPostForTest();

        PostDetailsDto detailsDto = PostMapper.INST.postToPostDetailsDto(post);

        assertThat(detailsDto).isNotNull();
        assertThat(detailsDto.id()).isEqualTo(UUID.fromString("693e3e0b-1254-4ef7-8830-3b92c74f8d42"));
        assertThat(detailsDto.createdAt()).isEqualTo(LocalDateTime.of(2024, Month.OCTOBER, 1, 9, 0, 0));
        assertThat(detailsDto.updatedAt()).isEqualTo(LocalDateTime.of(2024, Month.OCTOBER, 12, 19, 32, 4));
        assertThat(detailsDto.title()).isEqualTo("some post");
        assertThat(detailsDto.body()).isEqualTo("post body");
    }

    @Test
    void updatePostFromDto() {
        Post post = createPostForTest();
        PostCreateUpdateDto updateDto = new PostCreateUpdateDto("Updated title", "Updated Body!");

        PostMapper.INST.updatePostFromDto(post, updateDto);

        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(UUID.fromString("693e3e0b-1254-4ef7-8830-3b92c74f8d42"));
        assertThat(post.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, Month.OCTOBER, 1, 9, 0, 0));
        assertThat(post.getUpdatedAt()).isEqualTo(LocalDateTime.of(2024, Month.OCTOBER, 12, 19, 32, 4));
        assertThat(post.getTitle()).isEqualTo("Updated title");
        assertThat(post.getBody()).isEqualTo("Updated Body!");
    }

    private Post createPostForTest() {
        UUID id = UUID.fromString("693e3e0b-1254-4ef7-8830-3b92c74f8d42");
        LocalDateTime createdAt = LocalDateTime.of(2024, Month.OCTOBER, 1, 9, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, Month.OCTOBER, 12, 19, 32, 4);

        return Post.builder().id(id).createdAt(createdAt).updatedAt(updatedAt).title("some post").body("post body").build();
    }
}