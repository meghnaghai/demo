package com.sapient.credit.rest;

import com.sapient.credit.domain.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {

    final List<ErrorDTO.ApiErrors> errors = exception.getBindingResult().getFieldErrors().stream()
      .map(ex -> ErrorDTO.ApiErrors.builder()
        .field(ex.getField())
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

  @ExceptionHandler(MissingRequestHeaderException.class)
  protected ResponseEntity<ErrorDTO> handleMissingRequestHeaderException(final MissingRequestHeaderException exception) {

    final List<ErrorDTO.ApiErrors> errors = List.of(ErrorDTO.ApiErrors.builder()
      .field(exception.getHeaderName())
      .message(exception.getMessage())
      .build());

    log.error("Error: {}", errors);

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ErrorDTO.builder()
        .errors(errors)
        .build());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<ErrorDTO> handleMissingRequestHeaderException(final DataIntegrityViolationException exception) {

    final List<ErrorDTO.ApiErrors> errors = List.of(ErrorDTO.ApiErrors.builder()
      .message(exception.getMessage())
      .build());

    log.error("Error: {}", errors);

    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ErrorDTO.builder()
        .errors(errors)
        .build());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorDTO> handleMissingRequestHeaderException(final Exception exception) {

    final List<ErrorDTO.ApiErrors> errors = List.of(ErrorDTO.ApiErrors.builder()
      .message(exception.getMessage())
      .build());

    log.error("Error: {}", errors);

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(ErrorDTO.builder()
        .errors(errors)
        .build());
  }
}
