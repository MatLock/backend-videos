package com.backend.vids.entity;

import com.backend.vids.controller.request.VideoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "VIDEOS")
@Getter
@Setter
public class Video {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "LINK")
  private String link;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FOLDER_ID", referencedColumnName = "ID")
  private Folder folder;



  public static Video of(VideoRequest videoRequest, Folder f){
    Video v = new Video();
    v.setName(videoRequest.getName());
    v.setLink(videoRequest.getLink());
    v.setFolder(f);
    return v;
  }

}
