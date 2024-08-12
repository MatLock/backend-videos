package com.backend.vids.controller.reponse;

import com.backend.vids.entity.Video;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponse {

  private Long id;
  private String name;
  private String link;

  public static VideoResponse of (Video v){
    VideoResponse response = new VideoResponse();
    response.setId(v.getId());
    response.setName(v.getName());
    response.setLink(v.getLink());
    return response;
  }

}
