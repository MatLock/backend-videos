package com.backend.vids.controller.request;

import com.backend.vids.entity.Role;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRoleToUserRequest {

  private List<String> roles;

}
