package com.sapient.credit.service;

import com.sapient.credit.BaseTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptionServiceTest extends BaseTestConfig {
  @Autowired
  private EncryptionService encryptionService;

  @Test
  void givenSensitiveDataWhenEncryptCalledThenEncryptionSuccess() {
    String data = "4988357151";
    String encryptedString = encryptionService.encrypt(data);
    String decryptedString = encryptionService.decrypt(encryptedString);
    assertThat(encryptedString).isNotEqualTo(data);
    assertThat(decryptedString).isEqualTo(data);
  }

  @Test
  void givenSensitiveDataContainsSpacesWhenEncryptCalledThenEncryptionSuccess() {
    String data = " 4988357151 ";
    String encryptedString = encryptionService.encrypt(data);
    String decryptedString = encryptionService.decrypt(encryptedString);
    assertThat(encryptedString).isNotEqualTo(data.trim());
    assertThat(decryptedString).isEqualTo(data.trim());
  }

}
