package com.smartosc.fintech.lms.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiValidationError<T> implements ApiSubError {
  private String object;

  private String field;

  private T rejectedValue;

  private String message;

  public ApiValidationError(String object, String message) {
    this.object = object;
    this.message = message;
  }
}
