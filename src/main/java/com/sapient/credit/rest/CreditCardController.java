package com.sapient.credit.rest;

import com.sapient.credit.model.dto.CreditDTO;
import com.sapient.credit.model.dto.GetCreditDTO;
import com.sapient.credit.service.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/credit-card")
public class CreditCardController {

  private CreditCardService creditCardService;

  public CreditCardController(CreditCardService creditCardService) {
    this.creditCardService = creditCardService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addCreditCard(@RequestBody @Valid CreditDTO creditDTO){
    creditCardService.createCreditCard(creditDTO);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetCreditDTO getCreditCards(){

    return GetCreditDTO.builder()
    .creditCards(Collections.emptyList())
      .build();
  }

}
