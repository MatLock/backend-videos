package com.backend.vids.exception;

public class UnAuthorizedException extends  RuntimeException{

  public UnAuthorizedException(String reason){
    super(reason);
  }
}
