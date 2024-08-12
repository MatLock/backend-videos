package com.backend.vids.entity;

import com.backend.vids.controller.request.FolderCreationRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "FOLDERS")
@Setter
@Getter
public class Folder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "ROLE_REQUIRED")
  @Enumerated(EnumType.STRING)
  private Role roleRequired;
  @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Video> videos = new ArrayList<>();

  public static Folder of(FolderCreationRequest folderRequest) {
    Folder f = new Folder();
    f.setName(folderRequest.getName());
    f.setRoleRequired(Role.valueOf(folderRequest.getRoleRequired()));
    f.setVideos(new ArrayList<>());
    return f;
  }
}
