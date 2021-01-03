package com.sapient.credit.rest;

import com.sapient.credit.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException cve) {

    final List<ErrorDTO.ApiErrors> errors = cve.getConstraintViolations().stream()
      .map(ex -> ErrorDTO.ApiErrors.builder()
        .field(ex.getPropertyPath().toString())
        .message(ex.getMessage())
        .build())
      .collect(Collectors.toList());

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ErrorDTO.builder()
      .errors(errors)
      .build());
  }
}
