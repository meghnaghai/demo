package com.sapient.credit.service;


import com.sapient.credit.domain.repositories.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdempotenceService {
  private CreditCardRepository creditCardRepository;

  public IdempotenceService(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  public boolean checkIfDuplicate(UUID requestIdenifier) {
    return creditCardRepository.findById(requestIdenifier).isPresent();
  }
}
