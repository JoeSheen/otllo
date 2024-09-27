package com.shoejs.otllo.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    private final UUID id = UUID.fromString("fec26fd5-3575-4865-bb3b-b078ca2a82e5");

    private final String baseRequest = "/api/v1/users/";

    private MockMvc mockMvc;

    private final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    @BeforeEach
    void setUp() {
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetUserDetailsForProfile() throws Exception {
        when(userService.getUserDetailsForProfile(id)).thenReturn(buildUserDetailsForTest(true));

        String getRequest = baseRequest + id + "/profile";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(getRequest)
                .contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect((status().isOk())).andReturn();

        UserDetailsDto userDetails = mapper.readValue(result.getResponse().getContentAsString(), UserDetailsDto.class);
        assertUserDetailsDto(userDetails, true);
    }

    @Test
    void testToggleUserVisibility() throws Exception {
        when(userService.toggleUserVisibility(id, false)).thenReturn(buildUserDetailsForTest(false));

        String toggleVisibilityRequest = baseRequest + "togglevisibility/" + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(toggleVisibilityRequest)
                .param("visibility", "false").contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

        UserDetailsDto userDetails = mapper.readValue(result.getResponse().getContentAsString(), UserDetailsDto.class);
        assertUserDetailsDto(userDetails, false);
    }

    @Test
    void testUpdateUserProfile() throws Exception {
        UserUpdateDto updateDto = new UserUpdateDto("Charles", "Simmons", "charles.simmons@protonmail.com", "070123456789");

        when(userService.updateUserProfile(id, updateDto)).thenReturn(buildUserDetailsForTest(true));

        String updateUserProfileRequest = baseRequest + "/" + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(updateUserProfileRequest)
                .content(mapper.writeValueAsString(updateDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect((status().isOk())).andReturn();

        UserDetailsDto userDetails = mapper.readValue(result.getResponse().getContentAsString(), UserDetailsDto.class);
        assertUserDetailsDto(userDetails, true);
    }

    @Test
    void testUpdateUserProfileReturnsBadRequest() throws Exception {
        UserUpdateDto updateDto = new UserUpdateDto("", "", "", "");

        String updateUserProfileRequest = baseRequest + "/" + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(updateUserProfileRequest)
                .content(mapper.writeValueAsString(updateDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect((status().isBadRequest())).andReturn();

        assertThat(result.getResponse().getContentAsString()).isBlank();
        assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
    }

    @Test
    @Disabled("disabled till proper implementation is done")
    void testAddFriendToUser() {
        fail();
    }

    @Test
    void testDeleteUserByIdReturnsTrue() throws Exception {
        when(userService.deleteUserById(id)).thenReturn(true);

        String deleteRequest = baseRequest + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteRequest)
                .contentType(APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testDeleteUserByIdReturnsFalse() throws Exception {
        when(userService.deleteUserById(id)).thenReturn(false);

        String deleteRequest = baseRequest + id;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(deleteRequest)
                .contentType(APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound());
    }

    private void assertUserDetailsDto(UserDetailsDto userDetailsDto, boolean expectedVisibility) {
        assertThat(userDetailsDto).isNotNull();
        assertThat(userDetailsDto.id()).isEqualTo(UUID.fromString("fec26fd5-3575-4865-bb3b-b078ca2a82e5"));
        assertThat(userDetailsDto.firstName()).isEqualTo("Charles");
        assertThat(userDetailsDto.lastName()).isEqualTo("Simmons");
        assertThat(userDetailsDto.dateOfBirth()).isEqualTo(LocalDate.of(1984, Month.APRIL, 10));
        assertThat(userDetailsDto.gender()).isEqualTo("MALE");
        assertThat(userDetailsDto.email()).isEqualTo("charles.simmons@protonmail.com");
        assertThat(userDetailsDto.phoneNumber()).isEqualTo("070123456789");
        assertThat(userDetailsDto.username()).isEqualTo("ProSimmons");
        assertThat(userDetailsDto.profileImage()).isEqualTo("/some/path/");
        assertThat(userDetailsDto.friends()).isEmpty();
        assertThat(userDetailsDto.visible()).isEqualTo(expectedVisibility);
    }

    private UserDetailsDto buildUserDetailsForTest(boolean visibility) {
        LocalDate dateOfBirth = LocalDate.of(1984, Month.APRIL, 10);
        Set<UserDetailsDto> friends = new HashSet<>();

        return new UserDetailsDto(id, "Charles", "Simmons", dateOfBirth, "MALE",
                "charles.simmons@protonmail.com", "070123456789", "ProSimmons",
                "/some/path/", friends, visibility);
    }
}
