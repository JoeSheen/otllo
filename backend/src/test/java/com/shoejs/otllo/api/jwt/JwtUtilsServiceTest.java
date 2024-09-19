package com.shoejs.otllo.api.jwt;

import com.shoejs.otllo.api.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsServiceTest {

    private final JwtUtilsService jwtUtilsService = new JwtUtilsService();

    private final UUID id = UUID.fromString("f399e47a-7cfe-45ce-a6ff-b9215991fc3e");

    private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJqdGkiOiJiYWY5YjM0MS1iZTQwLTRhODMtOGRmZC0xZjMxYmYyM2M4NGYiL" +
            "CJpc3MiOiJPVExMTyIsInN1YiI6IkFyY0ZveCIsImF1ZCI6WyJvdGxsby5jb" +
            "20iXSwiZXhwIjoxNzI2NzE3NjkyLCJuYmYiOjE3MjY2ODg3MTIsImlhdCI6MTcyNjY" +
            "4ODg5Miwicm9sZSI6IlJPTEVfVVNFUiIsInVzZXJfaWQiOiJmMzk5ZTQ3YS03Y2ZlLT" +
            "Q1Y2UtYTZmZi1iOTIxNTk5MWZjM2UifQ.eJReuGV-kU2CM6oYSHEEl6NmPJnZc6J1DE1jWZCdfqs";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtilsService, "secret", "LoremIpsumDolorSitAmetConsecteturAdipiscingElit");
    }

    @Test
    void testExtractTokenFromRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("AUTHORIZATION", "Bearer " + token);

        String jwt = jwtUtilsService.extractTokenFromRequest(request);

        assertThat(jwt).isNotNull();
        assertThat(jwt.length()).isEqualTo(367);
        assertThat(jwt.startsWith("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.")).isTrue();
    }

    @Test
    void testVerifyToken() {
        String jwt = jwtUtilsService.generateToken("ArcFox", id);

        Claims claims = jwtUtilsService.verifyToken(jwt);

        assertThat(claims).isNotNull();
        assertThat(claims.size()).isEqualTo(9);
        assertThat(claims.getAudience()).isEqualTo(Set.of("otllo.com"));
        assertThat(claims.getIssuer()).isEqualTo("OTLLO");
        assertThat(claims.getIssuedAt()).isBefore(Date.from(Instant.now()));
        assertThat(claims.getExpiration()).isAfter(Date.from(Instant.now()));
        assertThat(claims.getNotBefore()).isBefore(Date.from(Instant.now()));
        assertThat(claims.get("role")).isEqualTo("ROLE_USER");
        assertThat(claims.getSubject()).isEqualTo("ArcFox");
        assertThat(claims.get("user_id")).isEqualTo("f399e47a-7cfe-45ce-a6ff-b9215991fc3e");
        assertThat(claims.getId()).isNotNull();
    }

    @Test
    void testVerifyTokenThrowsException() {
        assertThatThrownBy(() -> jwtUtilsService.verifyToken(token))
                .isInstanceOf(JwtAuthenticationException.class).hasMessageContainingAll("JWT expired", "at 2024-09-19T03:48:12.000Z.");
    }

    @Test
    void testGenerateToken() {
        String jwt = jwtUtilsService.generateToken("ArcFox", id);

        assertThat(jwt).isNotNull();
        assertThat(jwt.length()).isEqualTo(367);
        assertThat(jwt.startsWith("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9")).isTrue();
    }
}
