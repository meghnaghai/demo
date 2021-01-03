package com.sapient.credit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.credit.BaseTestConfig;
import com.sapient.credit.domain.dto.CreditCardDTO;
import com.sapient.credit.domain.entity.CreditCard;
import com.sapient.credit.service.CreditCardService;
import com.sapient.credit.testdata.TestDataCreditCardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sapient.credit.testdata.TestDataCreditCard.createCreditCard;
import static com.sapient.credit.testdata.TestDataCreditCardDTO.createCreditCardDTO;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class CreditCardControllerTest extends BaseTestConfig {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private CreditCardService creditCardService;

  @Autowired
  private ObjectMapper objectMapper;

  private UUID requestIdentifier;

  @BeforeEach
  void init() {
    requestIdentifier = UUID.randomUUID();
  }

  @Test
  void givenValidCreditCardNumberThenPostCreditCardRespondsWith201() throws Exception {

    final CreditCardDTO creditCardDTO = TestDataCreditCardDTO.createCreditCardDTO();

    //When
    doNothing()
      .when(creditCardService)
      .createCreditCard(creditCardDTO, requestIdentifier);

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  //TODO - implement
  //  @Test
  void givenInvalidCreditCardNumberThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setCardNumber("4988357151"));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("cardNumber")));

  }

  @Test
  void givenCreditCardNumberWithCharThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setCardNumber("4A98835H7151C"));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors[0].field",
      equalTo("cardNumber")));
  }

  @Test
  void givenCreditCardNumberDigitsGreaterThan19ThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setCardNumber("16345680213348946820"));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("cardNumber")));
  }

  @Test
  void givenCreditCardNumberEqualTo0ThenPostCreditCardRespondsWith201() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setCardNumber("0"));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @Test
  void givenNullCreditCardLimitThenPostCreditCardRespondsWith201() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setLimit(null));
    final CreditCardDTO creditCardDTO1 = createCreditCardDTO(dto -> dto.setLimit(0.00));

    //When
    doNothing()
      .when(creditCardService)
      .createCreditCard(creditCardDTO1, requestIdentifier);

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @Test
  void givenNegativeCreditCardLimitThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setLimit(-1000.00));

    //When
    doNothing()
      .when(creditCardService)
      .createCreditCard(creditCardDTO, requestIdentifier);

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("limit")));
  }

  @Test
  void givenNullGivenNameThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setGivenName(null));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("givenName")));
  }

  @Test
  void givenEmptyGivenNameThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setGivenName(""));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("givenName")));
  }

  @Test
  void givenSpaceGivenNameThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO(dto -> dto.setGivenName(" "));

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", requestIdentifier)
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("givenName")));
  }

  @Test
  void given2RecordExistsThenGetCreditCardWithoutPageSizeAndNumberRespondsWith200() throws Exception {

    List<CreditCard> creditCardList = IntStream.range(0, 2)
      .mapToObj(index -> createCreditCard())
      .collect(Collectors.toList());

    doReturn(creditCardList).when(creditCardService).readCreditCardData(0, 10);

    //Then
    mockMvc.perform(get("/v1/credit-cards")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.creditCards",
        hasSize(2)));
  }

  @Test
  void given2RecordExistsThenGetCreditCardWithPageSizeAndNumberRespondsWith200() throws Exception {

    List<CreditCard> creditCardList = IntStream.range(0, 2)
      .mapToObj(index -> createCreditCard())
      .collect(Collectors.toList());

    doReturn(creditCardList).when(creditCardService).readCreditCardData(1, 3);

    //Then
    mockMvc.perform(get("/v1/credit-cards?page=1&size=3")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.creditCards",
        hasSize(2)));
  }

  @Test
  void givenNoRecordExistsThenGetCreditCardRespondsWith200() throws Exception {

    doReturn(Collections.emptyList()).when(creditCardService).readCreditCardData(0, 10);

    //Then
    mockMvc.perform(get("/v1/credit-cards")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.creditCards",
        empty()));
  }

  @Test
  void givenMissingIdempotencyKeyThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO();

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .header("Idempotency-Key", "")
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("Idempotency-Key")));
  }

  @Test
  void givenNoIdempotencyKeyHeaderThenPostCreditCardRespondsWith400() throws Exception {

    final CreditCardDTO creditCardDTO = createCreditCardDTO();

    //Then
    mockMvc.perform(post("/v1/credit-cards")
      .content(mapper.writeValueAsString(creditCardDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].field",
        equalTo("Idempotency-Key")));
  }

//  private Integer[] toIntArray(String cardNumber) {
//    return Arrays.stream(cardNumber.split("")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
//  }

  @Test
  void givenApplicationIsUpThenGetHealthRespondsWith200() throws Exception {

    //Then
    mockMvc.perform(get("/actuator/health")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}
