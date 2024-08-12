package com.backend.vids.entity;

public enum Role {
  BONDS("BONDS"),
  BONDS_ADV("BONDS_ADV"),
  ADM("ADM");

  private String value;

  Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public String toString() {
    return String.valueOf(this.value);
  }

}
