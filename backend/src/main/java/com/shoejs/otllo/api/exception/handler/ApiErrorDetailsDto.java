package com.shoejs.otllo.api.exception.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Details object returned when an error occurs and an exception is thrown
 *
 * @param errorTimestamp time when the error happened
 * @param errorCode code for the error
 * @param errorMessage message from the error or exception
 * @param errorPath path being requested when the error occurred
 */
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
