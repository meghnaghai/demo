package com.sapient.credit.validator;

import com.sapient.credit.service.Luhn10ValidatorService;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class ValidCreditNumberValidator implements ConstraintValidator<ValidCreditNumber, String> {

  private Luhn10ValidatorService luhn10ValidatorService;

  public ValidCreditNumberValidator(Luhn10ValidatorService luhn10ValidatorService) {
    this.luhn10ValidatorService = luhn10ValidatorService;
  }

  public boolean isValid(String creditNumber, ConstraintValidatorContext context) {

    if (StringUtils.isBlank(creditNumber)
      || creditNumber.chars().anyMatch(ch -> !Character.isDigit(ch))
      || creditNumber.length() > 19) {

      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("CreditCard Number allows upto 19 digits. No Characters.")
        .addConstraintViolation();
      return false;
    }

    return luhn10ValidatorService.validate(creditNumber);
  }

}
