package com.mycompany.handler;

import com.mycompany.exception.ApiErrorResponse;
import com.mycompany.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleApplicationException(final UserException userException,
                                                        final HttpServletRequest request) {

        String uuid = UUID.randomUUID().toString();

        ApiErrorResponse response = new ApiErrorResponse(uuid, userException.getErrorCode(),
                userException.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(response, userException.getHttpStatus());

    }
}
