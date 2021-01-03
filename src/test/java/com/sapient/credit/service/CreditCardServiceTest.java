package com.sapient.credit.service;

import com.sapient.credit.BaseTestConfig;
import com.sapient.credit.dao.CreditCardDAO;
import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.entity.CreditCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static com.sapient.credit.testdata.TestDataCreditCard.createCreditCard;
import static com.sapient.credit.testdata.TestDataCreditCardDTO.createCreditCardDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreditCardServiceTest extends BaseTestConfig {

  @Autowired
  private CreditCardService creditCardService;

  @MockBean
  private CreditCardDAO creditCardDAO;

  @MockBean
  private IdempotenceService idempotenceService;

  private UUID requestIdentifier;

  @BeforeEach
  void init() {
    requestIdentifier = UUID.randomUUID();
    doReturn(false).when(idempotenceService).checkIfDuplicate(requestIdentifier);
  }

  @Test
  void givenValidCreditCardDataWhenCreateCreditCardThenSuccessAndCreditNumberEncryptedInDb() {
    CreditCardDTO creditCardDTO = createCreditCardDTO();

    doNothing().when(creditCardDAO).addCreditCard(any(CreditCard.class));

    Assertions.assertDoesNotThrow(() -> creditCardService.createCreditCard(creditCardDTO, requestIdentifier));
  }

  @Test
  void givenValidCreditCardDataWithNullLimitWhenCreateCreditCardThenSuccessAndCreditNumberEncryptedInDb() {
    CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setLimit(null));

    doNothing().when(creditCardDAO).addCreditCard(any(CreditCard.class));

    Assertions.assertDoesNotThrow(() -> creditCardService.createCreditCard(creditCardDTO, requestIdentifier));
    assertThat(creditCardDTO.getLimit()).isEqualTo(0.00);
  }

  @Test
  void givenRequestIsAlreadyCreatedWhenCreditCardAddedThenDoNothing() {
    CreditCardDTO creditCardDTO = createCreditCardDTO();
    final CreditCard creditCard = createCreditCard(creditCardDTO, requestIdentifier);

    doReturn(true)
      .when(idempotenceService)
      .checkIfDuplicate(requestIdentifier);

    Assertions.assertDoesNotThrow(() -> creditCardService.createCreditCard(creditCardDTO, requestIdentifier));

    verify(creditCardDAO, times(0)).addCreditCard(creditCard);
  }
}
