package com.sapient.credit.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCreditNumberValidator.class)
public @interface ValidCreditNumber {
  String message() default "CreditNumber is not valid as per Luhn 10";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
