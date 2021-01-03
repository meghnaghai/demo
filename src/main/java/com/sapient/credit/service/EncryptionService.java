package com.sapient.credit.service;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
  //TODO: look for another way
  public String encrypt(final String data) {
    String trimmedData = data.trim();
    int maxIndexForEncryption = Integer.min(3, trimmedData.length() / 2);

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder
      .append(trimmedData.substring(0, maxIndexForEncryption))
      .append("*".repeat(trimmedData.length() - maxIndexForEncryption));

    return stringBuilder.toString();
  }
}
