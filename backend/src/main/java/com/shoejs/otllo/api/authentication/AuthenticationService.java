package com.shoejs.otllo.api.authentication;

import com.shoejs.otllo.api.exception.DuplicateEntityException;
import com.shoejs.otllo.api.jwt.JwtUtilsService;
import com.shoejs.otllo.api.user.User;
import com.shoejs.otllo.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtUtilsService jwtUtilsService;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationMapper mapper;

    public AuthenticationDetailsDto signup(SignupCredentialsDto credentialsDto) {
        if (userRepository.existsByEmail(credentialsDto.email())) {
            throw new DuplicateEntityException("Email already registered");
        }
        User user = userRepository.save(mapper.signupCredentialsDtoToUser(credentialsDto));
        String jwt = jwtUtilsService.generateToken(user.getUsername(), user.getId());
        return mapper.userToAuthenticationDetailsDto(jwt, user);
    }

    public AuthenticationDetailsDto login(LoginCredentialsDto credentialsDto) {
        UsernamePasswordAuthenticationToken usernamePasswordToken =
                new UsernamePasswordAuthenticationToken(credentialsDto.username(), credentialsDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtilsService.generateToken(user.getUsername(), user.getId());
        return mapper.userToAuthenticationDetailsDto(jwt, user);
    }
}
