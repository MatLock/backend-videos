package com.backend.vids.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoRequest {

  @NotBlank
  private String name;
  @NotBlank
  private String link;

}
