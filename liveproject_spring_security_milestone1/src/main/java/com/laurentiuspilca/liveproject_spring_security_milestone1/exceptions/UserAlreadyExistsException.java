package com.laurentiuspilca.liveproject_spring_security_milestone1.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
