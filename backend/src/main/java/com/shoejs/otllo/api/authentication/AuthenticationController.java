package com.shoejs.otllo.api.authentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationDetailsDto signup(@Valid @RequestBody SignupCredentialsDto credentialsDto) {
        return authenticationService.signup(credentialsDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationDetailsDto login(@Valid @RequestBody LoginCredentialsDto credentialsDto) {
        return authenticationService.login(credentialsDto);
    }
}
