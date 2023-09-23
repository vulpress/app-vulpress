package hu.aestallon.vulpress.app.rest;

import hu.aestallon.vulpress.app.domain.ConstraintViolationException;
import hu.aestallon.vulpress.app.domain.ForbiddenOperationException;
import hu.aestallon.vulpress.app.rest.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

  private static ApiError errorOf(HttpStatus httpStatus, String message, WebRequest request) {
    return new ApiError()
        .status(httpStatus.value())
        .timestamp(LocalDateTime.now())
        .message(message)
        .description(request.getDescription(false));
  }

  @ExceptionHandler(ForbiddenOperationException.class)
  public ResponseEntity<ApiError> forbiddenOperationException(ForbiddenOperationException e,
                                                              WebRequest request) {
    final ApiError error = errorOf(HttpStatus.UNAUTHORIZED, e.getMessage(), request);
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> constraintViolationException(ConstraintViolationException e,
                                                               WebRequest request) {
    final ApiError error = errorOf(HttpStatus.CONFLICT, e.getMessage(), request);
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }
}
