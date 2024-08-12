package com.backend.vids.controller.reponse;

import com.backend.vids.entity.Folder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderResponse {

  private Long id;
  private String name;

  public static FolderResponse of(Folder folder) {
    FolderResponse response = new FolderResponse();
    response.setId(folder.getId());
    response.setName(folder.getName());
    return response;
  }
}
