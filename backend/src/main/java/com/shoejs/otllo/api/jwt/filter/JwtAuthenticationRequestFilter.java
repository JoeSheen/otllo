package com.shoejs.otllo.api.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoejs.otllo.api.exception.handler.ApiErrorDetailsDto;
import com.shoejs.otllo.api.jwt.JwtUtilsService;
import com.shoejs.otllo.api.user.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationRequestFilter extends OncePerRequestFilter {

    private final JwtUtilsService jwtUtilsService;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationRequestFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtUtilsService.extractTokenFromRequest(request);
            if (token != null) {
                Claims claims = jwtUtilsService.verifyToken(token);
                String subject = claims.getSubject();
                if (subject != null && !subject.isBlank()) {
                    User user = (User) userDetailsService.loadUserByUsername(subject);
                    if (user != null && isUnauthenticated()) {
                        successfulAuthentication(user, request);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            this.handleAuthenticationException(request, response, ex);
        }
    }

    private boolean isUnauthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void successfulAuthentication(User user, HttpServletRequest request) {
        logger.info("Authentication Successful");
        UsernamePasswordAuthenticationToken token =
                UsernamePasswordAuthenticationToken.authenticated(user, user, user.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private void handleAuthenticationException(HttpServletRequest request,
            HttpServletResponse response, Exception ex) throws IOException {
        ApiErrorDetailsDto apiError = new ApiErrorDetailsDto(LocalDateTime.now(),
                HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
