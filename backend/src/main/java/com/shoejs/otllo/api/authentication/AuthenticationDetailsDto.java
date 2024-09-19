package com.shoejs.otllo.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoejs.otllo.api.user.UserDetailsDto;

public record AuthenticationDetailsDto(
        @JsonProperty("access_token")
        String token,
        @JsonProperty("userDetails")
        UserDetailsDto userDetailsDto
) {}
