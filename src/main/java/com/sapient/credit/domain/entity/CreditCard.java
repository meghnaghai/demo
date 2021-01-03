package com.sapient.credit.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "credit_card_details")
public class CreditCard {
  @Id
  @NotNull
  private UUID requestIdentifier;
  @NotNull
  private String givenName;
  @NotNull
  private String cardNumber;
  @NotNull
  private Double creditLimit;
  @NotNull
  private Timestamp createdAt;
  private Timestamp modifiedAt;
}
