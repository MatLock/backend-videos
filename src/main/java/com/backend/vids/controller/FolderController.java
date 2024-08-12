package com.backend.vids.controller;

import com.backend.vids.controller.reponse.FolderResponse;
import com.backend.vids.controller.reponse.GlobalRestResponse;
import com.backend.vids.controller.reponse.VideoResponse;
import com.backend.vids.controller.request.FolderCreationRequest;
import com.backend.vids.controller.request.VideoToFolderRequest;
import com.backend.vids.controller.validator.RoleRequestValidator;
import com.backend.vids.entity.Folder;
import com.backend.vids.entity.Video;
import com.backend.vids.services.FolderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folders")
@Validated
public class FolderController {

  private FolderService folderService;
  private RoleRequestValidator roleRequestValidator;

  public FolderController(FolderService folderService, RoleRequestValidator roleRequestValidator) {
    this.folderService = folderService;
    this.roleRequestValidator = roleRequestValidator;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('ADM')")
  public ResponseEntity<GlobalRestResponse<Folder>> create(@Valid @RequestBody FolderCreationRequest folderRequest){
    roleRequestValidator.validate(List.of(folderRequest.getRoleRequired()));
    Folder folder = folderService.createWith(folderRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(new GlobalRestResponse<>(true,folder));
  }

  @PostMapping("/{id}/attachVideo")
  @PreAuthorize("hasAuthority('ADM')")
  public ResponseEntity<GlobalRestResponse<Void>> attachVideoToFolder(@Min(value = 0) @PathVariable("id") Long folderId, @Valid @RequestBody VideoToFolderRequest videoToFolderRequest) {
    folderService.attachVideo(folderId, videoToFolderRequest.getVideos());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/all")
  public ResponseEntity<GlobalRestResponse<List<FolderResponse>>> obtainAllFoldersForUser(){
    List<Folder> folders = folderService.listAllForUser();
    return ResponseEntity.ok(new GlobalRestResponse<>(Boolean.TRUE,folders.stream().map(f -> FolderResponse.of(f)).toList()));
  }

  @GetMapping("/{id}/videos")
  public ResponseEntity<GlobalRestResponse<List<VideoResponse>>> obtainVideosOfFolder(@PathVariable("id") @Min(value = 0) Long folderId){
    List<Video> videos = folderService.obtainAllVideosOfFolder(folderId);
    return ResponseEntity.ok(new GlobalRestResponse<>(Boolean.TRUE,videos.stream().map(v -> VideoResponse.of(v)).toList()));
  }

}
