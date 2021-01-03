package com.sapient.credit.service;

import com.sapient.credit.BaseTestConfig;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptionServiceTest extends BaseTestConfig {
  @Autowired
  private EncryptionService encryptionService;

  void givenSensitiveDataWhenEncryptCalledThenEncryptionSuccess() {
    String data = "4988357151";
    assertThat(encryptionService.encrypt(data)).isNotEqualTo(data).hasSize(10);
  }

  void givenSensitiveDataContainsSpacesWhenEncryptCalledThenEncryptionSuccess() {
    String data = " 4988357151 ";
    String encryptedData = encryptionService.encrypt(data);
    assertThat(encryptedData).isNotEqualTo(data).hasSize(10);
  }

}
