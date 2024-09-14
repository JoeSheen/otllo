package com.shoejs.otllo.api.jwt.filter;

import com.shoejs.otllo.api.exception.JwtAuthenticationException;
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

@Component
@RequiredArgsConstructor
public class JwtAuthenticationRequestFilter extends OncePerRequestFilter {

    private final JwtUtilsService jwtUtilsService;

    private final UserDetailsService userDetailsService;

    //private final HandlerExceptionResolver exceptionResolver;

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
        } catch (Exception ex) {
            throw new JwtAuthenticationException(ex.getMessage());
            // TODO: add exceptionResolver to handle any exceptions thrown during jwt filter
            // exceptionResolver...
        }
        filterChain.doFilter(request, response);
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
}
