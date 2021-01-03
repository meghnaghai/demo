package com.sapient.credit.dao;

import com.sapient.credit.BaseTestConfig;
import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.domain.repositories.CreditCardRepository;
import com.sapient.credit.testdata.TestDataCreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CreditCardDAOTest extends BaseTestConfig {
  @Autowired
  private CreditCardDAO creditCardDAO;

  @Autowired
  private CreditCardRepository creditCardRepository;

  @Test
  void givenCreditCardEntityWhenSavedThenSuccess() {
    CreditCard creditCard = TestDataCreditCard.createCreditCard();

    assertDoesNotThrow(() -> creditCardDAO.addCreditCard(creditCard));
    Optional<CreditCard> creditCardOptional = creditCardRepository.findById(creditCard.getRequestIdentifier());
    assertThat(creditCardOptional).isNotNull();
    assertThat(creditCardOptional.get()).isNotNull().isEqualTo(creditCard);
  }

  @Test
  void givenNRecordsInDbWhenGetCreditCardDetailsInvokedThenSuccess() {
    IntStream.range(0, 4)
      .mapToObj(index -> TestDataCreditCard.createCreditCard())
      .forEach(creditCard -> creditCardDAO.addCreditCard(creditCard));

    assertThat(creditCardRepository.findAll()).hasSize(5);

    List<CreditCard> creditCardList = assertDoesNotThrow(() -> creditCardDAO.getCreditCardDetails(0, 2));
    assertThat(creditCardList).isNotEmpty().hasSize(2);


    creditCardList = assertDoesNotThrow(() -> creditCardDAO.getCreditCardDetails(1, 2));
    assertThat(creditCardList).isNotEmpty().hasSize(2);


    creditCardList = assertDoesNotThrow(() -> creditCardDAO.getCreditCardDetails(2, 2));
    assertThat(creditCardList).isNotEmpty().hasSize(1);


    creditCardList = assertDoesNotThrow(() -> creditCardDAO.getCreditCardDetails(3, 2));
    assertThat(creditCardList).isEmpty();
  }

  @Test
  void givenNoRecordsInDbWhenGetCreditCardDetailsInvokedThenReturnEmptyList() {
    List<CreditCard> creditCardList = assertDoesNotThrow(() -> creditCardDAO.getCreditCardDetails(0, 10));
    assertThat(creditCardList).isEmpty();
  }

}
