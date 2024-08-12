package com.backend.vids.handler;

import com.backend.vids.controller.reponse.ErrorRestResponse;
import com.backend.vids.exception.AlreadyExistsException;
import com.backend.vids.exception.RolInvalidException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String INVALID_FIELDS_ERROR_MSG = "Invalid Fields Found";

  @ExceptionHandler(value = {AccessDeniedException.class})
  protected ResponseEntity<ErrorRestResponse> handleForbiddenError(RuntimeException ex) {
    ErrorRestResponse response = new ErrorRestResponse(null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN_403).body(response);
  }


  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<ErrorRestResponse> handleNotFoundException(RuntimeException ex) {
    ErrorRestResponse response = new ErrorRestResponse(null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND_404).body(response);
  }

  @ExceptionHandler(value = {AlreadyExistsException.class, RolInvalidException.class})
  protected ResponseEntity<ErrorRestResponse> handleDomainExceptions(RuntimeException ex) {
    ErrorRestResponse response = new ErrorRestResponse(null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).body(response);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST_400)
        .body(new ErrorRestResponse(errors,INVALID_FIELDS_ERROR_MSG ));
  }


}
