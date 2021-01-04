package com.sapient.credit.domain.dto;

import com.sapient.credit.validator.ValidCreditNumber;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
public class CreditCardDTO {
  @Parameter(
    description = "Given Name for credit card holder.",
    required = true)
  @NotBlank
  private String givenName;

  @Parameter(
    description = "Credit Card Number upto 19 digits.",
    required = true)
  @ValidCreditNumber
  private String cardNumber;

  @Parameter(
    description = "Credit Card creditLimit. Default 0. ")
  @DecimalMin(value = "0.00")
  private Double limit;
}
