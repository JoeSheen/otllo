package com.shoejs.otllo.api.jwt;

import com.shoejs.otllo.api.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class JwtUtilsService {

    @Value("${api.jwt.secret}")
    private String secret;

    private static final String BEARER = "Bearer";

    private static final String ISSUER = "OTLLO";

    private static final String AUDIENCE = "otllo.com";

    public String extractTokenFromRequest(HttpServletRequest request) {
        String jwt = null;
        String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER)) {
            jwt = authHeader.substring(7);
        }
        return jwt;
    }

    public Claims verifyToken(String token) {
        return parseToken(token).getPayload();
    }

    public String generateToken(String username, UUID userId) {
        Date expirationDate = Date.from(Instant.now().plus(8, HOURS));
        Date notBeforeDate = Date.from(Instant.now().minus(3, MINUTES));
        Date issuedAtDate = Date.from(Instant.now());

        return Jwts.builder().header().and().id(UUID.randomUUID().toString()).issuer(ISSUER)
                .subject(username).audience().add(AUDIENCE).and().expiration(expirationDate)
                .notBefore(notBeforeDate).issuedAt(issuedAtDate).claim("role", "ROLE_USER")
                .claim("user_id", userId).signWith(getSecretSigningKey(), Jwts.SIG.HS256).compact();
    }

    private Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parser().verifyWith(getSecretSigningKey()).requireIssuer(ISSUER)
                    .requireAudience(AUDIENCE).build().parseSignedClaims(token);
        } catch (Exception ex) {
            throw new JwtAuthenticationException(ex.getMessage());
        }
    }

    private SecretKey getSecretSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }
}
