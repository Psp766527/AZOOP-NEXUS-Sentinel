package com.azoop.it.asis.nexus.sentinel.global.exception;

import com.azoop.it.asis.nexus.sentinel.model.DTO.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Global exception handler for all controllers in the application.
 * <p>
 * This class handles various exception scenarios and returns standardized
 * error responses to the client with appropriate HTTP status codes.
 * </p>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles authentication failures due to bad credentials.
     *
     * @param ex      the exception instance
     * @param request the incoming HTTP request
     * @return a 401 Unauthorized error response
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", ex, request);
    }

    /**
     * Handles cases where a username or client was not found.
     *
     * @param ex      the exception instance
     * @param request the incoming HTTP request
     * @return a 404 Not Found error response
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "User or client not found", ex, request);
    }

    /**
     * Handles validation errors when method arguments are not valid.
     *
     * @param ex      the exception instance
     * @param request the incoming HTTP request
     * @return a 400 Bad Request error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", ex, request);
    }

    /**
     * Handles all other unhandled exceptions in the system.
     *
     * @param ex      the exception instance
     * @param request the incoming HTTP request
     * @return a 500 Internal Server Error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUnhandled(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex, request);
    }

    /**
     * Constructs a standardized {@link ErrorResponse} with relevant metadata.
     *
     * @param status  the HTTP status to return
     * @param message a custom error message
     * @param ex      the caught exception
     * @param request the current HTTP request
     * @return a {@link ResponseEntity} containing the error payload
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, Exception ex, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message != null ? message : ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, status);
    }
}
