package com.shoejs.otllo.api.exception.handler;

import com.shoejs.otllo.api.exception.DuplicateEntityException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalApiExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateEntityException.class)
    public ApiErrorDetailsDto handleDuplicateEntityException(HttpServletRequest request, DuplicateEntityException ex) {
        return buildApiErrorDetailsDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiErrorDetailsDto handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        return buildApiErrorDetailsDto(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
    }

    private ApiErrorDetailsDto buildApiErrorDetailsDto(int code, String message, String path) {
        ApiErrorDetailsDto errorDetails = new ApiErrorDetailsDto(LocalDateTime.now(), code, message, path);
        logger.info("{}", errorDetails);
        return errorDetails;
    }
}
