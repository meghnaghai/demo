package com.sapient.credit.service;

import com.sapient.credit.BaseTestConfig;
import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.domain.repositories.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

public class IdempotenceServiceTest extends BaseTestConfig {
  @Autowired
  private IdempotenceService idempotenceService;
  @MockBean
  private CreditCardRepository creditCardRepository;
  private UUID requestIdentifier;

  @BeforeEach
  void init() {
    requestIdentifier = UUID.randomUUID();
  }

  @Test
  void givenRequestIsServicedWhenDuplicateRequestIsSentThenReturnTrue() {
    doReturn(Optional.of(new CreditCard())).when(creditCardRepository).findById(requestIdentifier);

    assertThat(idempotenceService.checkIfDuplicate(requestIdentifier)).isEqualTo(true);
  }

  @Test
  void givenNewRequestWhenRequestIsSentThenReturnFalse() {
    doReturn(Optional.empty()).when(creditCardRepository).findById(requestIdentifier);

    assertThat(idempotenceService.checkIfDuplicate(requestIdentifier)).isEqualTo(false);
  }
}
