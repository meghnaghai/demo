package com.sapient.credit.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Luhn10ValidatorService {

  public boolean validate(String creditCardNumber) {

    List<Integer> reverseCardNumber = Arrays.stream(creditCardNumber.split(""))
      .mapToInt(Integer::parseInt)
      .boxed()
      .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
        Collections.reverse(list);
        return list;
      }));

    int sum = 0;

    for (int i = 0; i < reverseCardNumber.size(); i++) {
      int placeholderValue = reverseCardNumber.get(i);

      if (i % 2!=0) {
        String[] chars = String.valueOf(placeholderValue * 2).split("");

        placeholderValue = Arrays.stream(chars)
          .mapToInt(Integer::parseInt)
          .sum();

      }

      sum += placeholderValue;
    }

    if (sum % 10==0) {
      return true;
    }

    return false;
  }
}
