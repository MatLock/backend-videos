package com.backend.vids.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JWTUser {

  @JsonProperty("subject")
  private String subject;
  @JsonProperty("secret")
  private String secret;
}
