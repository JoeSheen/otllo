package com.shoejs.otllo.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoejs.otllo.api.user.UserDetailsDto;

/**
 * Dto returned by {@link AuthenticationController} upon successful authentication
 *
 * @param token object used to prove users identify in subsequent requests to the server
 * @param userDetailsDto details of the user that has just been authenticated
 */
public record AuthenticationDetailsDto(
        @JsonProperty("access_token")
        String token,
        @JsonProperty("userDetails")
        UserDetailsDto userDetailsDto
) {}
