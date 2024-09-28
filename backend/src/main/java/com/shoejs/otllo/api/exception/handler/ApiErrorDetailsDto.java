package com.shoejs.otllo.api.exception.handler;

import java.time.LocalDateTime;

public record ApiErrorDetailsDto(
        LocalDateTime errorTimestamp,
        int errorCode,
        String errorMessage,
        String path
) {}
