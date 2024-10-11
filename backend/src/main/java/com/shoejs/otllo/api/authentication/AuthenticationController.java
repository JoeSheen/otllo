package com.shoejs.otllo.api.authentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for authenticating a user. This Class exposes post methods for signup and login.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Method used to authenticate a user on signup
     *
     * @param credentialsDto object used for creating a new user
     * @return resource produced upon successful authentication
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationDetailsDto signup(@Valid @RequestBody SignupCredentialsDto credentialsDto) {
        return authenticationService.signup(credentialsDto);
    }

    /**
     * Method used to authenticate a user who is already known to the application and is returning
     *
     * @param credentialsDto object used to authenticate the user on login
     * @return resource produced upon successful authentication
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationDetailsDto login(@Valid @RequestBody LoginCredentialsDto credentialsDto) {
        return authenticationService.login(credentialsDto);
    }
}
