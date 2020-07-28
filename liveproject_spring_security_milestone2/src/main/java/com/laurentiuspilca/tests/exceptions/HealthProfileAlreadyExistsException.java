package com.laurentiuspilca.tests.exceptions;

public class HealthProfileAlreadyExistsException extends RuntimeException {

  public HealthProfileAlreadyExistsException(String message) {
    super(message);
  }
}
