package com.sapient.credit.domain.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
public class ErrorDTO {

  @Parameter(
    description = "The list of errors.",
    required = true)
  private List<ApiErrors> errors;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ApiErrors {
    @Parameter(
      description = "The field for which the validation constraint is applicable",
      required = true)
    private String field;

    @Parameter(
      description = "Human readable validation constraint description to the relating specific instance at hand",
      required = true)
    private String message;
  }
}
