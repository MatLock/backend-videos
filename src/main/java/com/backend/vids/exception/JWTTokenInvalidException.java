package com.backend.vids.exception;

public class JWTTokenInvalidException extends  RuntimeException{

  public JWTTokenInvalidException(String reason){
    super(reason);
  }
}
