package com.sapient.credit.dao;

import com.sapient.credit.BaseTestConfig;
import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.domain.repositories.CreditCardRepository;
import com.sapient.credit.testdata.TestDataCreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  void givenExistingCreditNumberInDbWhenSaveCreditCardDetailsInvokedThenThrowException() {
    List<CreditCard> creditCardList = addTestData();
    creditCardList.get(0).setRequestIdentifier(UUID.randomUUID());

    assertThrows(DataIntegrityViolationException.class, () -> creditCardDAO.addCreditCard(creditCardList.get(0)));
  }

  @Test
  void givenNRecordsInDbWhenGetCreditCardDetailsInvokedThenSuccess() {

    addTestData();

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

  @Transactional
  private List<CreditCard> addTestData() {
    creditCardRepository.deleteAll();

    List<CreditCard> creditCardList = IntStream.range(0, 5)
      .mapToObj(index -> TestDataCreditCard.createCreditCard())
      .collect(Collectors.toList());
    creditCardList.get(0).setCardNumber("6097214362");
    creditCardList.get(1).setCardNumber("6755505275");
    creditCardList.get(2).setCardNumber("5945508405");
    creditCardList.get(3).setCardNumber("0577895279");
    creditCardList.get(4).setCardNumber("9110351443");

    creditCardRepository.saveAll(creditCardList);
    return creditCardList;
  }

  @Test
  void givenNoRecordsInDbWhenGetCreditCardDetailsInvokedThenReturnEmptyList() {
    List<CreditCard> creditCardList = assertDoesNotThrow(() -> creditCardDAO.getCreditCardDetails(0, 10));
    assertThat(creditCardList).isEmpty();
  }

}
