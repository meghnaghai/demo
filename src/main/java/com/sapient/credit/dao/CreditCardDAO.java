package com.sapient.credit.dao;

import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.domain.repositories.CreditCardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditCardDAO {
  private CreditCardRepository creditCardRepository;

  public CreditCardDAO(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  @Transactional
  public void addCreditCard(CreditCard creditCard) {
    creditCardRepository.save(creditCard);
  }

  @Transactional(readOnly = true)
  public List<CreditCard> getCreditCardDetails(int pageNumber, int pageSize) {

    return creditCardRepository
      .findAll(PageRequest.of(pageNumber, pageSize, Sort.by("modifiedAt").descending()))
      .getContent();
  }
}
