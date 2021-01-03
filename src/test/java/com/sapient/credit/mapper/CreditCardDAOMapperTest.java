package com.sapient.credit.mapper;

import com.sapient.credit.BaseTestConfig;
import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.entity.CreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.sapient.credit.testdata.TestDataCreditCardDTO.createCreditCardDTO;
import static org.assertj.core.api.Assertions.assertThat;

public class CreditCardDAOMapperTest extends BaseTestConfig {
  @Autowired
  private CreditCardDAOMapper creditCardDAOMapper;

  @Test
  void givenValidCreditCardDTOWhenToCreditCardDAOCalledThenCreditCardObjectIsCreated() {
    final CreditCardDTO creditCardDTO = createCreditCardDTO();
    final UUID requestIdentifier = UUID.randomUUID();

    final CreditCard creditCard = creditCardDAOMapper.toCreditCard(creditCardDTO, requestIdentifier);

    assertThat(creditCard).isNotNull();
    assertThat(creditCard.getCardNumber()).isNotNull().isNotEqualTo(creditCardDTO.getCardNumber());
    assertThat(creditCard.getCreatedAt()).isNotNull();
    assertThat(creditCard.getGivenName()).isNotNull().isEqualTo(creditCardDTO.getGivenName());
    assertThat(creditCard.getCreditLimit()).isNotNull().isEqualTo(creditCardDTO.getLimit());
    assertThat(creditCard.getModifiedAt()).isNotNull().isEqualTo(creditCard.getCreatedAt());
    assertThat(creditCard.getRequestIdentifier()).isNotNull().isEqualTo(requestIdentifier);
  }

}
