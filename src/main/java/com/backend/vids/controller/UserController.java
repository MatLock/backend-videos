package com.backend.vids.controller;

import com.backend.vids.controller.reponse.GlobalRestResponse;
import com.backend.vids.controller.request.AddRoleToUserRequest;
import com.backend.vids.controller.request.UserRequest;
import com.backend.vids.controller.validator.RoleRequestValidator;
import com.backend.vids.entity.User;
import com.backend.vids.services.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/users")
@Validated
public class UserController {
  // adm key 1dec836c-0ce5-4bf4-b08f-f758d4b566f8

  private UserService userService;
  private RoleRequestValidator roleRequestValidator;

  public UserController(UserService userService, RoleRequestValidator roleRequestValidator){
    this.userService = userService;
    this.roleRequestValidator = roleRequestValidator;
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ADM')")
  public ResponseEntity<GlobalRestResponse<List<User>>> getAllUsers(){
    List<User> users = userService.listAllUsers();
    return ResponseEntity.ok(GlobalRestResponse.<List<User>>builder()
            .success(Boolean.TRUE)
            .data(users)
        .build());
  }

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('ADM')")
  public ResponseEntity<GlobalRestResponse<User>> createUser(@Valid @RequestBody UserRequest request){
    User user = userService.createUserWith(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new GlobalRestResponse<>(Boolean.TRUE,user));
  }

  @PostMapping("/{id}/attachRole")
  @PreAuthorize("hasAuthority('ADM')")
  public ResponseEntity<GlobalRestResponse<Void>> attachRoleToUser(@PathVariable("id") Long userId, @Valid @RequestBody AddRoleToUserRequest request){
    roleRequestValidator.validate(request.getRoles());
    userService.attachRoleToUser(userId, request.getRoles());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }







}
