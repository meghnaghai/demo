package com.sapient.credit.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.*;

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
	@Size(max=19)
	private Integer[] cardNumber;

  @Parameter(
    description = "Credit Card limit. Default 0. ")
	@Positive
	private Double limit;
}
