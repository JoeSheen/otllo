package com.shoejs.otllo.api.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shoejs.otllo.api.user.UserDetailsDto;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    private final String baseRequest = "/api/v1/auth";

    private MockMvc mockMvc;

    private final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    private final UUID id = UUID.fromString("2f9c32bb-3935-451b-8255-5898bf1ea794");

    private final LocalDate dateOfBirth = LocalDate.of(1993, Month.JULY, 25);

    private final Set<UserDetailsDto> friends = new HashSet<>();

    @BeforeEach
    void setUp() {
        AuthenticationController controller = new AuthenticationController(authenticationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSignup() throws Exception {
        when(authenticationService.signup(any(SignupCredentialsDto.class))).thenReturn(buildAuthenticationDetailsForTest());

        SignupCredentialsDto credentialsDto = new SignupCredentialsDto("Roxanna", "Montez",
                dateOfBirth, "FEMALE", "roxanna.montez@protonmail.com", "070123456789",
                "Colith93", "password", "/some/path/", true);

        String signupRequestPath = baseRequest + "/signup";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(signupRequestPath)
                .content(mapper.writeValueAsString(credentialsDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect((status().isCreated())).andReturn();

        AuthenticationDetailsDto authDetails = mapper.readValue(result.getResponse().getContentAsString(), AuthenticationDetailsDto.class);
        assertAuthenticationDetailsDto(authDetails);
    }

    @Test
    void testLogin() throws Exception {
        when(authenticationService.login(any(LoginCredentialsDto.class))).thenReturn(buildAuthenticationDetailsForTest());

        LoginCredentialsDto credentialsDto = new LoginCredentialsDto("Colith93", "password");

        String loginRequestPath = baseRequest + "/login";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(loginRequestPath)
                .content(mapper.writeValueAsString(credentialsDto)).contentType(APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andExpect((status().isOk())).andReturn();
        AuthenticationDetailsDto authDetails = mapper.readValue(result.getResponse().getContentAsString(), AuthenticationDetailsDto.class);
        assertAuthenticationDetailsDto(authDetails);
    }

    private void assertAuthenticationDetailsDto(AuthenticationDetailsDto dto) {
        assertThat(dto).isNotNull();
        assertThat(dto.token()).isEqualTo("test-jwt");
        assertUserDetailsDto(dto.userDetailsDto());
    }

    private void assertUserDetailsDto(UserDetailsDto details) {
        assertThat(details).isNotNull();
        assertThat(details.id()).isEqualTo(UUID.fromString("2f9c32bb-3935-451b-8255-5898bf1ea794"));
        assertThat(details.firstName()).isEqualTo("Roxanna");
        assertThat(details.lastName()).isEqualTo("Montez");
        assertThat(details.dateOfBirth()).isEqualTo(LocalDate.of(1993, Month.JULY, 25));
        assertThat(details.gender()).isEqualTo("FEMALE");
        assertThat(details.email()).isEqualTo("roxanna.montez@protonmail.com");
        assertThat(details.phoneNumber()).isEqualTo("070123456789");
        assertThat(details.username()).isEqualTo("Colith93");
        assertThat(details.profileImage()).isEqualTo("/some/path/");
        assertThat(details.friends()).isEmpty();
        assertThat(details.visible()).isTrue();
    }

    private AuthenticationDetailsDto buildAuthenticationDetailsForTest() {
        UserDetailsDto userDetails = new UserDetailsDto(id, "Roxanna", "Montez", dateOfBirth,
                "FEMALE", "roxanna.montez@protonmail.com", "070123456789", "Colith93",
                "/some/path/", friends, true);
        return new AuthenticationDetailsDto("test-jwt", userDetails);
    }
}
