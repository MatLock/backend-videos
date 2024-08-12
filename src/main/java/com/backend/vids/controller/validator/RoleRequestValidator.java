package com.backend.vids.controller.validator;

import static java.lang.String.format;

import com.backend.vids.entity.Role;
import com.backend.vids.exception.RolInvalidException;
import com.backend.vids.utils.Constant;
import java.util.List;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

@Component
public class RoleRequestValidator {

  public void validate(List<String> roles){
    if(roles == null || roles.isEmpty()){
      throw new RolInvalidException(Constant.HTTP_400_ROLE_EMPTY);
    }
    roles.forEach(role -> innerValidate(role));
  }

  private void innerValidate(String role) {
    if (!EnumUtils.isValidEnum(Role.class, role)){
     throw new RolInvalidException(format(Constant.HTTP_400_INVALID_ROLE, role));
    }
  }

}
