package com.laurentiuspilca.liveproject_spring_security_milestone1.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {

  public ClientAlreadyExistsException(String message) {
    super(message);
  }
}
