package com.sapient.credit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.credit.model.dto.CreditDTO;
import com.sapient.credit.service.CreditCardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.sapient.credit.testdata.TestCreditDTOData.createCreditDTO;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CreditCardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private CreditCardService creditCardService;

  @Test
  void givenValidCreditCardNumberThenPostCreditCardRespondsWith201() throws Exception {

    final CreditDTO creditDTO = createCreditDTO();

    //When
    doNothing()
      .when(creditCardService)
      .createCreditCard(creditDTO);

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }


  @Test
  void givenInvalidCreditCardNumberThenPostCreditCardRespondsWith400() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setCardNumber(toIntArray("4988357151")));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenCreditCardNumberDigitsGreaterThan19ThenPostCreditCardRespondsWith400() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setCardNumber(toIntArray("16345680213348946820")));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenCreditCardNumberEqualTo0ThenPostCreditCardRespondsWith201() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setCardNumber(toIntArray("0")));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @Test
  void givenNoCreditCardLimitThenPostCreditCardRespondsWith201() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setLimit(null));

    //When
    doNothing()
      .when(creditCardService)
      .createCreditCard(creditDTO);

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @Test
  void givenNegativeCreditCardLimitThenPostCreditCardRespondsWith201() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setLimit(-1000.00));

    //When
    doNothing()
      .when(creditCardService)
      .createCreditCard(creditDTO);

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenNullGivenNameThenPostCreditCardRespondsWith400() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setGivenName(null));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenEmptyGivenNameThenPostCreditCardRespondsWith400() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setGivenName(""));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenSpaceGivenNameThenPostCreditCardRespondsWith400() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setGivenName(" "));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void givenNoDataExistsThenGetCreditCardRespondsWith200() throws Exception {

    //Then
    mockMvc.perform(get("/v1/credit-card")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.creditCards",
        empty()));
  }

  private Integer[] toIntArray(String cardNumber) {
    return Arrays.stream(cardNumber.split("")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
  }

  @Test
  void givenApplicationIsUpThenGetHealthRespondsWith200() throws Exception {

    //Then
    mockMvc.perform(get("/actuator/health")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}
