package com.sapient.credit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.credit.model.dto.CreditDTO;
import com.sapient.credit.service.CreditCardService;
import com.sapient.credit.testdata.TestCreditDTOData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;

import static com.sapient.credit.testdata.TestCreditDTOData.createCreditDTO;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
  public void givenValidCreditCardNumberThenPostCreditCardRespondsWith201() throws Exception {

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
  public void givenInvalidCreditCardNumberThenPostCreditCardRespondsWith400() throws Exception {

    final CreditDTO creditDTO = createCreditDTO(dto -> dto.setCardNumber(new BigInteger("4988357151")));

    //Then
    mockMvc.perform(post("/v1/credit-card")
      .content(mapper.writeValueAsString(creditDTO))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @Test
  public void givenNoCreditCardLimitThenPostCreditCardRespondsWith201() throws Exception {

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

}
