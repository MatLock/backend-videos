package com.backend.vids.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

  @NotBlank
  private String name;
  @NotBlank
  private String password;
  @NotBlank
  private String email;


}
