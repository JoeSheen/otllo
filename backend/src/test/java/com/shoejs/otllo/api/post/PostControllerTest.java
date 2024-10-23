package com.shoejs.otllo.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    private final UUID id = UUID.fromString("dfe8c6bb-f21a-41ec-8a83-7cb255f47625");

    private final String baseRequestPath = "/api/v1/posts/";

    private final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        PostController controller = new PostController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreatePost() throws Exception {
        when(postService.createPost(any(PostCreateUpdateDto.class), any(User.class)))
                .thenReturn(buildPostDetailsForTest("Title", "body text"));

        PostCreateUpdateDto createPostDto = new PostCreateUpdateDto("Title", "body text");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseRequestPath)
                .content(mapper.writeValueAsString(createPostDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect((status().isCreated())).andReturn();

        PostDetailsDto postDetails = mapper.readValue(result.getResponse().getContentAsString(), PostDetailsDto.class);
        assertPostDetailsDto(postDetails, "Title", "body text");
    }

    @Test
    void testCreatePostReturnsBadRequest() throws Exception {
        PostCreateUpdateDto createPostDto = new PostCreateUpdateDto("", "");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseRequestPath)
                .content(mapper.writeValueAsString(createPostDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isBlank();
        assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
    }

    @Test
    void testUpdatePost() throws Exception {
        when(postService.updatePost(any(UUID.class), any(PostCreateUpdateDto.class), any(User.class)))
                .thenReturn(buildPostDetailsForTest("Updated Title", "Updated body text"));

        String putRequest = baseRequestPath + id;
        PostCreateUpdateDto updatePostDto = new PostCreateUpdateDto("Updated Title", "Updated body text");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(putRequest)
                .content(mapper.writeValueAsString(updatePostDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        PostDetailsDto postDetails = mapper.readValue(result.getResponse().getContentAsString(), PostDetailsDto.class);
        assertPostDetailsDto(postDetails, "Updated Title", "Updated body text");
    }

    @Test
    void testUpdatePostReturnsBadRequest() throws Exception {
        String putRequest = baseRequestPath + id;

        PostCreateUpdateDto updatePostDto = new PostCreateUpdateDto("   ", "       ");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(putRequest)
                .content(mapper.writeValueAsString(updatePostDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isBlank();
        assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
    }

    @Test
    void testGetPostById() throws Exception {
        when(postService.getPostById(any(UUID.class))).thenReturn(buildPostDetailsForTest("get Post", "some text"));

        String getByIdRequest = baseRequestPath + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(getByIdRequest)
                .contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        PostDetailsDto postDetails = mapper.readValue(result.getResponse().getContentAsString(), PostDetailsDto.class);
        assertPostDetailsDto(postDetails, "get Post", "some text");
    }

    @Test
    void testDeletePostByIdReturnsOk() throws Exception {
        when(postService.deletePostById(any(UUID.class), any(User.class))).thenReturn(true);

        String deleteRequest = baseRequestPath + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteRequest)
                .contentType(APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testDeletePostByIdReturnsNotFound() throws Exception {
        when(postService.deletePostById(any(UUID.class), any(User.class))).thenReturn(false);

        String deleteRequest = baseRequestPath + id;
        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.delete(deleteRequest)
                .content(APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilders).andDo(print()).andExpect(status().isNotFound());
    }

    private void assertPostDetailsDto(PostDetailsDto postDetailsDto, String expectedTitle, String expectedBody) {
        assertThat(postDetailsDto).isNotNull();
        assertThat(postDetailsDto.id()).isEqualTo(UUID.fromString("dfe8c6bb-f21a-41ec-8a83-7cb255f47625"));
        assertThat(postDetailsDto.createdAt()).isEqualTo(LocalDateTime.of(2024, Month.OCTOBER, 20, 18, 0, 0));
        assertThat(postDetailsDto.updatedAt()).isEqualTo(LocalDateTime.of(2024, Month.OCTOBER, 23, 10, 47, 0));
        assertThat(postDetailsDto.title()).isEqualTo(expectedTitle);
        assertThat(postDetailsDto.body()).isEqualTo(expectedBody);
        assertThat(postDetailsDto.author()).isEqualTo("Andle96");
    }

    private PostDetailsDto buildPostDetailsForTest(String title, String body) {
        LocalDateTime createdAt = LocalDateTime.of(2024, Month.OCTOBER, 20, 18, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, Month.OCTOBER, 23, 10, 47, 0);
        return new PostDetailsDto(id, createdAt, updatedAt, title, body, "Andle96");
    }
}
