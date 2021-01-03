package com.sapient.credit.rest;

import com.sapient.credit.model.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {

    final List<ErrorDTO.ApiErrors> errors = exception.getAllErrors().stream()
      .map(ex -> ErrorDTO.ApiErrors.builder()
        .field(ex.getObjectName())
        .message(ex.getDefaultMessage())
        .build())
      .collect(Collectors.toList());

    log.error("Error: {}", errors);

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ErrorDTO.builder()
        .errors(errors)
        .build());
  }
}
