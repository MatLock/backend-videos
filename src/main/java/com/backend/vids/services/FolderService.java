package com.backend.vids.services;

import static com.backend.vids.utils.Constant.HTTP_400_ALREADY_EXISTS;
import static com.backend.vids.utils.Constant.HTTP_403_USER_FORBIDDEN_TO_FOLDER;
import static com.backend.vids.utils.Constant.HTTP_404_NOT_FOUND;
import static java.lang.String.format;

import com.backend.vids.controller.request.FolderCreationRequest;
import com.backend.vids.controller.request.VideoRequest;
import com.backend.vids.entity.Folder;
import com.backend.vids.entity.Role;
import com.backend.vids.entity.Video;
import com.backend.vids.exception.AlreadyExistsException;
import com.backend.vids.exception.ForbiddenException;
import com.backend.vids.exception.NotFoundException;
import com.backend.vids.repository.FolderRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FolderService {

  private FolderRepository folderRepository;

  public FolderService(FolderRepository repository){
    folderRepository = repository;
  }

  @Transactional
  public Folder createWith(FolderCreationRequest folderRequest) {
    if(folderRepository.findByName(folderRequest.getName()).isPresent()){
      throw new AlreadyExistsException(format(HTTP_400_ALREADY_EXISTS,"folder"));
    }
    Folder folder = Folder.of(folderRequest);
    return folderRepository.save(folder);
  }


  @Transactional
  public void attachVideo(Long folderId, List<VideoRequest> links) {
    Optional<Folder> folder = folderRepository.findById(folderId);
    if(folder.isEmpty()){
      throw new NotFoundException(HTTP_404_NOT_FOUND);
    }
    folder.get().getVideos().addAll(links.stream().map(v -> Video.of(v,folder.get())).toList());
    folderRepository.save(folder.get());
  }

  public List<Folder> listAllForUser() {
    List<GrantedAuthority> auths = (List) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    return folderRepository.findAllWithRoles(auths.stream().map(auth -> auth.getAuthority()).toList());
  }

  public List<Video> obtainAllVideosOfFolder(Long folderId) {
    List<GrantedAuthority> auths = (List) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    List<Role> userRoles = auths.stream().map(auth -> Role.valueOf(auth.getAuthority())).toList();
    Optional<Folder> folder = folderRepository.findById(folderId);
    if(folder.isEmpty()){
      throw new NotFoundException(format(HTTP_404_NOT_FOUND, "folder"));
    }
    if (!userRoles.contains(folder.get().getRoleRequired())){
      throw new ForbiddenException(HTTP_403_USER_FORBIDDEN_TO_FOLDER);
    }
    return folder.get().getVideos();
  }
}
