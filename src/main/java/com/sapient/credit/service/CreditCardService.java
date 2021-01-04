package com.sapient.credit.service;

import com.sapient.credit.dao.CreditCardDAO;
import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.mapper.CreditCardDAOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CreditCardService {
  private CreditCardDAO creditCardDAO;
  private CreditCardDAOMapper creditCardDAOMapper;
  private IdempotenceService idempotenceService;
  private EncryptionService encryptionService;

  public CreditCardService(final CreditCardDAO creditCardDAO,
                           final CreditCardDAOMapper creditCardDAOMapper,
                           final IdempotenceService idempotenceService,
                           final EncryptionService encryptionService) {
    this.creditCardDAO = creditCardDAO;
    this.creditCardDAOMapper = creditCardDAOMapper;
    this.idempotenceService = idempotenceService;
    this.encryptionService = encryptionService;
  }

  @Transactional
  public void createCreditCard(final CreditCardDTO creditCardDTO, UUID requestIdentifier) {
    if (idempotenceService.checkIfDuplicate(requestIdentifier)) {
      return;
    }

    if (Objects.isNull(creditCardDTO.getLimit())) {
      creditCardDTO.setLimit(0.00);
    }

    CreditCard creditCard = creditCardDAOMapper.toCreditCard(creditCardDTO, requestIdentifier);
    creditCardDAO.addCreditCard(creditCard);
  }

  public List<CreditCard> readCreditCardData(int pageNumber, int pageSize) {
    List<CreditCard> creditCardList = creditCardDAO.getCreditCardDetails(pageNumber, pageSize);
    creditCardList.forEach(creditCard -> {
      String decryptStr = encryptionService.decrypt(creditCard.getCardNumber());
      creditCard.setCardNumber(decryptStr);
    });
    return creditCardList;
  }
}
