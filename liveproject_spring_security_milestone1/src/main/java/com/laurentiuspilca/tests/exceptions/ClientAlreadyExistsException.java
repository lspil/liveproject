package com.laurentiuspilca.tests.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {

  public ClientAlreadyExistsException(String message) {
    super(message);
  }
}
