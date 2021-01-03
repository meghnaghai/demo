package com.sapient.credit.domain.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class CreditDTO {
  @Parameter(
    description = "Given Name for credit card holder.",
    required = true)
  @NotBlank
  private String givenName;

  @Parameter(
    description = "Credit Card Number upto 19 digits.",
    required = true)
  @Pattern(regexp = "^[0-9]{1,19}$")
  private String cardNumber;

  @Parameter(
    description = "Credit Card limit. Default 0. ")
  @Positive
  private Double limit;
}
