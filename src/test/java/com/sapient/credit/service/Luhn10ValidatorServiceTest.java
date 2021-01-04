package com.sapient.credit.service;

import com.sapient.credit.BaseTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class Luhn10ValidatorServiceTest extends BaseTestConfig {

  @Autowired
  private Luhn10ValidatorService luhn10ValidatorService;

  @Test
  void givenValidCreditNumberWhenValidateInvokedThenTrue() {
    assertThat(luhn10ValidatorService.validate("5014599392")).isTrue();
  }

  @Test
  void givenInvalidCreditNumberWhenValidateInvokedThenFalse() {
    assertThat(luhn10ValidatorService.validate("5014599393")).isFalse();
  }
}
