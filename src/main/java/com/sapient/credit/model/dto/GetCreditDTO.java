package com.sapient.credit.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCreditDTO {
  private List<CreditDTO> creditCards;
}
