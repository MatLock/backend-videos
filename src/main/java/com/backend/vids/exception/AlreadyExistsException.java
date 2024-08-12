package com.backend.vids.exception;

public class AlreadyExistsException extends RuntimeException{

  public AlreadyExistsException(String message) {
    super(message);
  }
}
