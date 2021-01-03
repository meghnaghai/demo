package com.sapient.credit.mapper;

import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.service.EncryptionService;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Component
public class CreditCardDAOMapper {
  private EncryptionService encryptionService;

  public CreditCardDAOMapper(EncryptionService encryptionService) {
    this.encryptionService = encryptionService;
  }

  public CreditCard toCreditCard(CreditCardDTO creditCardDTO, UUID requestIdentifier) {
    final CreditCard creditCard = new CreditCard();
    creditCard.setCardNumber(encryptionService.encrypt(creditCardDTO.getCardNumber()));
    creditCard.setCreatedAt(Timestamp.from(Instant.now()));
    creditCard.setGivenName(creditCardDTO.getGivenName());
    creditCard.setCreditLimit(creditCardDTO.getLimit());
    creditCard.setModifiedAt(creditCard.getCreatedAt());
    creditCard.setRequestIdentifier(requestIdentifier);
    return creditCard;
  }
}
