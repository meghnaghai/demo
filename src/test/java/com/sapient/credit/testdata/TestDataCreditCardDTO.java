package com.sapient.credit.testdata;

import com.sapient.credit.domain.dto.CreditCardDTO;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public class TestDataCreditCardDTO {
  public static CreditCardDTO createCreditCardDTO(final Consumer<CreditCardDTO> consumer) {
    CreditCardDTO creditCardDTO = new CreditCardDTO();
    creditCardDTO.setCardNumber("5014599392");
    creditCardDTO.setGivenName("ABC");
    creditCardDTO.setLimit(5000.00);

    if (nonNull(consumer)) {
      consumer.accept(creditCardDTO);
    }

    return creditCardDTO;
  }

  public static CreditCardDTO createCreditCardDTO() {
    return createCreditCardDTO(null);
  }
}
