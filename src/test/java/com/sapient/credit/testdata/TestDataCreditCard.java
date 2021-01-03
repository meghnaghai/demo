package com.sapient.credit.testdata;

import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.entity.CreditCard;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public class TestDataCreditCard {
  public static CreditCard createCreditCard(final Consumer<CreditCard> consumer) {
    CreditCard creditCard = new CreditCard();
    creditCard.setCardNumber("5014599392");
    creditCard.setGivenName("ABC");
    creditCard.setCreditLimit(5000.00);
    creditCard.setRequestIdentifier(UUID.randomUUID());
    creditCard.setCreatedAt(Timestamp.from(Instant.now()));
    creditCard.setModifiedAt(Timestamp.from(Instant.now()));

    if (nonNull(consumer)) {
      consumer.accept(creditCard);
    }

    return creditCard;
  }

  public static CreditCard createCreditCard() {
    return createCreditCard(null);
  }

  public static CreditCard createCreditCard(CreditCardDTO creditCardDTO, UUID requestId) {
    final CreditCard creditCard = new CreditCard();
    creditCard.setCardNumber(creditCardDTO.getCardNumber());
    creditCard.setCreatedAt(Timestamp.from(Instant.now()));
    creditCard.setGivenName(creditCardDTO.getGivenName());
    creditCard.setCreditLimit(creditCardDTO.getLimit());
    creditCard.setModifiedAt(creditCard.getCreatedAt());
    creditCard.setRequestIdentifier(requestId);
    return creditCard;
  }
}
