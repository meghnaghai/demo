package com.sapient.credit.testdata;

import com.sapient.credit.model.dto.CreditDTO;

import java.math.BigInteger;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public class TestCreditDTOData {
  public static CreditDTO createCreditDTO(Consumer<CreditDTO> consumer){
    CreditDTO creditDTO = new CreditDTO();
    creditDTO.setCardNumber(new BigInteger("5014599392"));
    creditDTO.setGivenName("ABC");
    creditDTO.setLimit(5000);

    if(nonNull(consumer)){
      consumer.accept(creditDTO);
    }

    return creditDTO;
  }

  public static CreditDTO createCreditDTO(){
    return createCreditDTO(null);
  }
}
