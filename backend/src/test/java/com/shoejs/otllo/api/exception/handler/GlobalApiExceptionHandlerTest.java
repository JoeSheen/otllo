package com.shoejs.otllo.api.exception.handler;

import com.shoejs.otllo.api.exception.DuplicateEntityException;
import com.shoejs.otllo.api.exception.InvalidRequestException;
import com.shoejs.otllo.api.exception.ResourceNotFoundException;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.*;

class GlobalApiExceptionHandlerTest {

    private final GlobalApiExceptionHandler apiExceptionHandler = new GlobalApiExceptionHandler();

    private final MockHttpServletRequest request = new MockHttpServletRequest();

    @BeforeEach
    void setUp() {
        request.setRequestURI("mock/request/uri/path");
    }

    @Test
    void testHandleDuplicateEntityException() {
        ApiErrorDetailsDto apiError = apiExceptionHandler.handleDuplicateEntityException(request, new DuplicateEntityException("Email already registered"));
        assertApiErrorDetailsDto(apiError, 400, "Email already registered");
    }

    @Test
    void testHandleResourceNotFoundException() {
        ApiErrorDetailsDto apiError = apiExceptionHandler.handleResourceNotFoundException(request, new ResourceNotFoundException("User with ID: [c0a801af-9254-1685-8192-5446c35a0000] not found"));
        assertApiErrorDetailsDto(apiError, 404, "User with ID: [c0a801af-9254-1685-8192-5446c35a0000] not found");
    }

    @Test
    void testHandleInvalidRequestException() {
        ApiErrorDetailsDto apiError = apiExceptionHandler.handleInvalidRequestException(request, new InvalidRequestException("Only the author can update a post"));
        assertApiErrorDetailsDto(apiError, 400, "Only the author can update a post");
    }

    private void assertApiErrorDetailsDto(ApiErrorDetailsDto apiErrorDto, int expectedCode, String expectedMessage) {
        assertThat(apiErrorDto).isNotNull();
        assertThat(apiErrorDto.errorTimestamp()).isCloseTo(LocalDateTime.now(), new TemporalUnitWithinOffset(5, SECONDS));
        assertThat(apiErrorDto.errorCode()).isEqualTo(expectedCode);
        assertThat(apiErrorDto.errorMessage()).isEqualTo(expectedMessage);
        assertThat(apiErrorDto.errorPath()).isEqualTo("mock/request/uri/path");
    }
}
