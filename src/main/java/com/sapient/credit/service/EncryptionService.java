package com.sapient.credit.service;

import com.sapient.credit.exception.EncryptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class EncryptionService {

  private static final byte[] keyBytes = new byte[]{'0', '2', '3', '4', '5', '6', '7', '8', '9', '1', '2', '3', '4', '5', '6', '7'};// your key;
  private static final byte[] ivBytes = new byte[]{'0', '4', '3', '6', '8', '0', '7', '9'};
  private final SecretKeySpec secretKey;

  public EncryptionService(@Value("credit-card.encryption.secret:dummyTest") String secret) {
    secretKey = getKey(secret);
  }

  private SecretKeySpec getKey(String myKey) {
    byte[] key = null;
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      return new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      throw new EncryptionException("Error encrypting Credit Number", e);
    } catch (UnsupportedEncodingException e) {
      throw new EncryptionException("Error encrypting Credit Number", e);
    }
  }

  //TODO: look for another way
  public String encrypt(final String unencryptedData) {

    try {

      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      String encryptedStr = Base64.getEncoder().encodeToString(cipher.doFinal(unencryptedData.trim().getBytes("UTF-8")));

      return encryptedStr;

    } catch (Exception e) {
      throw new EncryptionException("Error encrypting Credit Number", e);
    }
  }

  public String decrypt(final String encryptedString) {
    try {

      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      String decryptedStr = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedString.trim())));

      return decryptedStr;

    } catch (Exception e) {
      throw new EncryptionException("Error decrypting Credit Number", e);
    }
  }
}
