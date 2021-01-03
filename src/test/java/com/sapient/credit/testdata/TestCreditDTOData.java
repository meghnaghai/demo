package com.sapient.credit.testdata;

import com.sapient.credit.model.dto.CreditDTO;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public class TestCreditDTOData {
  public static CreditDTO createCreditDTO(final Consumer<CreditDTO> consumer) {
    CreditDTO creditDTO = new CreditDTO();
    creditDTO.setCardNumber("5014599392");
    creditDTO.setGivenName("ABC");
    creditDTO.setLimit(5000.00);

    if (nonNull(consumer)) {
      consumer.accept(creditDTO);
    }

    return creditDTO;
  }

  public static CreditDTO createCreditDTO() {
    return createCreditDTO(null);
  }
}
