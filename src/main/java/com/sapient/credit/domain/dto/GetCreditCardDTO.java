package com.sapient.credit.domain.dto;

import com.sapient.credit.domain.entity.CreditCard;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCreditCardDTO {
  private List<CreditCard> creditCards;
}
