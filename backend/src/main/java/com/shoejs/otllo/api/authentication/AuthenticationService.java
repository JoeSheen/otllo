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

/**
 * Service exposing actions that can be performed during authentication
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtUtilsService jwtUtilsService;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationMapper mapper;

    /**
     * Method for creating a new user and returning the required authentication details.
     *
     * @param credentialsDto details used to create the new user.
     * @throws DuplicateEntityException if the email contained in the {@link SignupCredentialsDto} is already registered
     * @return {@link AuthenticationDetailsDto} based off the provided fields
     */
    public AuthenticationDetailsDto signup(SignupCredentialsDto credentialsDto) {
        if (userRepository.existsByEmail(credentialsDto.email())) {
            throw new DuplicateEntityException("Email already registered");
        }
        User user = userRepository.save(mapper.signupCredentialsDtoToUser(credentialsDto));
        String jwt = jwtUtilsService.generateToken(user.getUsername(), user.getId());
        return mapper.userToAuthenticationDetailsDto(jwt, user);
    }

    /**
     * Method for authenticating a user that is already registered.
     *
     * @param credentialsDto details used to authenticate the returning user
     * @return {@link AuthenticationDetailsDto} for the returning user
     */
    public AuthenticationDetailsDto login(LoginCredentialsDto credentialsDto) {
        UsernamePasswordAuthenticationToken usernamePasswordToken =
                new UsernamePasswordAuthenticationToken(credentialsDto.username(), credentialsDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtilsService.generateToken(user.getUsername(), user.getId());
        return mapper.userToAuthenticationDetailsDto(jwt, user);
    }
}
