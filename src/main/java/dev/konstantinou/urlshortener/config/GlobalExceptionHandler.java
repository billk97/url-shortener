package dev.konstantinou.urlshortener.config;

import dev.konstantinou.urlshortener.dtos.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiError> unhandledExceptionHandler(Exception ex, WebRequest wr) throws Exception {
        throw ex;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<ApiError> badRequestHandler400(Exception ex, WebRequest wr) {
        var error = new ApiError(Instant.now(), ex.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            DisabledException.class,
            AccessDeniedException.class,
            AccountExpiredException.class,
            LockedException.class,
            CredentialsExpiredException.class,
            AccountExpiredException.class
    })
    public final ResponseEntity<ApiError> badRequestHandler403(Exception ex, WebRequest wr) {
        var error = new ApiError(Instant.now(), ex.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}