package com.shoejs.otllo.api.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shoejs.otllo.api.jwt.JwtUtilsService;
import com.shoejs.otllo.api.user.User;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationRequestFilterTest {

    @Mock
    private UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JwtUtilsService jwtUtilsService = new JwtUtilsService();

    private JwtAuthenticationRequestFilter filter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtilsService, "secret", "LoremIpsumDolorSitAmetConsecteturAdipiscingElit");
        objectMapper.registerModule(new JavaTimeModule());

        filter = new JwtAuthenticationRequestFilter(jwtUtilsService, userDetailsService, objectMapper);
    }

    @Test
    void testJwtAuthenticationRequestFilterWithValidToken() throws ServletException, IOException {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(User.builder().build());

        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = jwtUtilsService.generateToken("Whicar1988", UUID.fromString("4bfca228-86d4-400f-a134-9b434db7e408"));
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();
        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testJwtAuthenticationRequestFilterWithInvalidToken() throws ServletException, IOException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3MzQ0MjMzZC05YmE1LTQ4MmMtOGNjZi1m" +
                "Mjk3NzdmZjYxMGMiLCJpc3MiOiJPVExMTyIsInN1YiI6IldoaWNhcjE5ODgiLCJhdWQiOlsib3RsbG8uY29tIl0sIm" +
                "V4cCI6MTcyODAwMDkwOCwibmJmIjoxNzI3OTcxOTI4LCJpYXQiOjE3Mjc5NzIxMDgsInJvbGUiOiJST0xFX1VTRVIi" +
                "LCJ1c2VyX2lkIjoiNGJmY2EyMjgtODZkNC00MDBmLWExMzQtOWI0MzRkYjdlNDA4In0.wseyRf7NrHJhrBfBNPs2Wo" +
                "aQWjXKXJRrgIpq8iKwLkQ";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setRequestURI("mock/request/uri/path");
        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();
        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        assertThat(response.getStatus()).isEqualTo(401);
    }

    @Test
    void testCreateJwtAuthenticationRequestFilterTest() {
        assertThat(filter).isNotNull();
    }
}
