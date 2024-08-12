package com.backend.vids.services;

import static com.backend.vids.utils.Constant.HTTP_400_ALREADY_EXISTS;
import static java.lang.String.format;

import com.backend.vids.controller.request.UserRequest;
import com.backend.vids.entity.Role;
import com.backend.vids.entity.User;
import com.backend.vids.entity.UserRole;
import com.backend.vids.exception.AlreadyExistsException;
import com.backend.vids.exception.NotFoundException;
import com.backend.vids.repository.RoleRepository;
import com.backend.vids.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;

  public UserService(UserRepository repository, RoleRepository roleRepository){
    this.userRepository = repository;
    this.roleRepository = roleRepository;
  }

  public List<User> listAllUsers(){
    return userRepository.findAll();
  }


  @Transactional
  public User createUserWith(UserRequest request) {
    if(userRepository.findByEmail(request.getEmail()).isPresent()){
      throw new AlreadyExistsException(format(HTTP_400_ALREADY_EXISTS, "user"));
    }
    User user = User.of(request);
    return userRepository.save(user);
  }

  @Transactional
  public void attachRoleToUser(Long userId,List<String> roles) {
    Optional<User> user = userRepository.findById(userId);
    if(user.isEmpty()){
      throw new NotFoundException("user not found");
    }
    user.get().getRoles().addAll(obtainRolesToAdd(user.get(), roles.stream().map(rol -> Role.valueOf(rol)).toList()));
    userRepository.save(user.get());
  }

  private List<UserRole> obtainRolesToAdd(User user, List<Role> roles) {
    return roles
        .stream()
        .filter( role -> user.getRoles().stream().noneMatch(userRole -> userRole.getRole().equals(role)))
        .map( role -> roleRepository.findUserRoleByRole(role).get())
        .toList();
  }
}
