package com.backend.vids.security;

import com.backend.vids.entity.User;
import com.backend.vids.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorService {

  private UserRepository userRepository;

  public UserValidatorService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public boolean authenticate(String email, String secret){
    Optional<User> user = userRepository.findByEmail(email);
    if(user.isPresent()){
      return user.get().getEmail().equals(email) && user.get().getPassword().equals(secret);
    }
    return Boolean.FALSE;
  }

}
