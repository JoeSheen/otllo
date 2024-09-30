package com.shoejs.otllo.api.exception.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ApiErrorDetailsDto(
        @JsonProperty("timestamp")
        LocalDateTime errorTimestamp,
        @JsonProperty("status")
        int errorCode,
        @JsonProperty("message")
        String errorMessage,
        @JsonProperty("path")
        String errorPath
) {}
