package com.shoejs.otllo.api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

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
        return Jwts.parser().verifyWith(getSecretSigningKey()).requireIssuer(ISSUER)
                .requireAudience(AUDIENCE).build().parseSignedClaims(token).getPayload();
    }

    public String generateToken(String username) {
        return "";
    }

    private SecretKey getSecretSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }
}
