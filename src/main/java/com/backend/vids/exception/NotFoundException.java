package com.backend.vids.exception;

public class NotFoundException  extends  RuntimeException{

  public NotFoundException(String reason){
    super(reason);
  }

}
