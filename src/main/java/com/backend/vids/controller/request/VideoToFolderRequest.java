package com.backend.vids.controller.request;


import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoToFolderRequest {

  @NotNull
  private List<VideoRequest> videos;
}
