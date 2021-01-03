package com.sapient.credit.domain.dao;

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
public class CreditDAO {
  @Id
  @NotNull
  private UUID referenceNumber;
  @NotNull
  private String givenName;
  @NotNull
  private String cardNumber;
  @NotNull
  private Double limit;
  @NotNull
  private Timestamp createdAt;
  private Timestamp modifiedAt;
}
