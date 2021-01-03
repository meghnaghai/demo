package com.sapient.credit.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigInteger;

@Data
public class CreditDTO {
  @Parameter(
    description = "Given Name for credit card holder.",
    required = true)
  @NotEmpty
	private String givenName;

  @Parameter(
    description = "Credit Card Number upto 19 digits.",
    required = true)
	@Size(max = 19)
  @Positive
	private BigInteger cardNumber;

  @Parameter(
    description = "Credit Card limit. Default 0. ")
	@Min(value = 0L)
	private Double limit;
}
