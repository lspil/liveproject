package com.laurentiuspilca.tests.exceptions;

public class NonExistentHealthProfileException extends RuntimeException {

  public NonExistentHealthProfileException(String message) {
    super(message);
  }
}
