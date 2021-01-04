package com.sapient.credit.rest;

import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.dto.ErrorDTO;
import com.sapient.credit.domain.dto.GetCreditCardDTO;
import com.sapient.credit.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "Rest endpoint to register Credit card")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Created"),
    @ApiResponse(responseCode = "400", description = "Bad Request",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = ErrorDTO.class))}),
    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = ErrorDTO.class))})
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO,
                            @RequestHeader("Idempotency-Key") @NotNull UUID requestIdentifier) {
    creditCardService.createCreditCard(creditCardDTO, requestIdentifier);
  }

  @Operation(summary = "Rest endpoint to get all Credit cards")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetCreditCardDTO getCreditCards(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {

    return GetCreditCardDTO.builder()
      .creditCards(creditCardService.readCreditCardData(page, size))
      .build();
  }

}
