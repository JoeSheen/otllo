package com.shoejs.otllo.api.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class JwtUtilsService {

    @Value("${api.jwt.secret}")
    private String secret;

    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;

    private static final String BEARER_PREFIX = "Bearer ";

    private static final String ISSUER = "OTLLO";
}
