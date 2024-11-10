package com.shoejs.otllo.api.postcomment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoejs.otllo.api.common.CollectionDetailsDto;
import com.shoejs.otllo.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(MockitoExtension.class)
class PostCommentControllerTest {

    @Mock
    private PostCommentService postCommentService;

    private final UUID id = UUID.fromString("757d8115-cbd1-4751-8294-82d58a2d4a11");

    private final String baseRequestPath = "/api/v1/post-comment";

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        PostCommentController controller = new PostCommentController(postCommentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreatePostComment() throws Exception {
        when(postCommentService.createPostComment(any(PostCommentCreateDto.class), any(User.class)))
                .thenReturn(new PostCommentDetailsDto(id, "comment", "author"));

        PostCommentCreateDto createDto = new PostCommentCreateDto("comment", UUID.randomUUID());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseRequestPath)
                .content(mapper.writeValueAsString(createDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isCreated()).andReturn();

        PostCommentDetailsDto postCommentDetails = mapper.readValue(result.getResponse().getContentAsString(), PostCommentDetailsDto.class);
        assertPostCommentDetailsDto(postCommentDetails);
    }

    @Test
    void testGetAllPostComments() throws Exception {
        UUID postId = UUID.fromString("a0bc81b9-11fd-4a76-827e-5049d1e86569");

        List<PostCommentDetailsDto> details = List.of(new PostCommentDetailsDto(id, "comment", "author"));
        when(postCommentService.getAllPostComments(any(UUID.class), any(Integer.class), any(Integer.class)))
                .thenReturn(new CollectionDetailsDto<>(details, 0, 1, 1L));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(baseRequestPath)
                .param("searchValue", postId.toString()).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        CollectionDetailsDto<PostCommentDetailsDto> collectionDetailsDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(collectionDetailsDto).isNotNull();
        assertThat(collectionDetailsDto.details().size()).isEqualTo(1);
        assertPostCommentDetailsDto(collectionDetailsDto.details().get(0));
        assertThat(collectionDetailsDto.currentPage()).isEqualTo(0);
        assertThat(collectionDetailsDto.totalPages()).isEqualTo(1);
        assertThat(collectionDetailsDto.totalElements()).isEqualTo(1L);
    }

    @Test
    void deletePostCommentByIdReturnsOk() throws Exception {
        when(postCommentService.deletePostCommentById(any(UUID.class), any(User.class))).thenReturn(true);

        String deleteRequest = baseRequestPath + "/" + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteRequest)
                .contentType(APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deletePostCommentByIdReturnsNotFound() throws Exception {
        when(postCommentService.deletePostCommentById(any(UUID.class), any(User.class))).thenReturn(false);

        String deleteRequest = baseRequestPath + "/" + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteRequest)
                .contentType(APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound());
    }

    private void assertPostCommentDetailsDto(PostCommentDetailsDto postCommentDetailsDto) {
        assertThat(postCommentDetailsDto).isNotNull();
        assertThat(postCommentDetailsDto.id()).isEqualTo(UUID.fromString("757d8115-cbd1-4751-8294-82d58a2d4a11"));
        assertThat(postCommentDetailsDto.comment()).isEqualTo("comment");
        assertThat(postCommentDetailsDto.author()).isEqualTo("author");
    }
}
