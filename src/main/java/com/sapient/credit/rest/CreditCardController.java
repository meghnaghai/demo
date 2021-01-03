package com.sapient.credit.rest;

import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.dto.GetCreditCardDTO;
import com.sapient.credit.service.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/v1/credit-cards")
public class CreditCardController {

  private CreditCardService creditCardService;

  public CreditCardController(final CreditCardService creditCardService) {
    this.creditCardService = creditCardService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO,
                            @RequestHeader("Idempotency-Key") @NotNull UUID requestIdentifier) {
    creditCardService.createCreditCard(creditCardDTO, requestIdentifier);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetCreditCardDTO getCreditCards(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {

    return GetCreditCardDTO.builder()
      .creditCards(creditCardService.readCreditCardData(page, size))
      .build();
  }

}
