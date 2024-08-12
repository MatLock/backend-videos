package com.backend.vids.repository;

import com.backend.vids.entity.Role;
import com.backend.vids.entity.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

  Optional<UserRole> findUserRoleByRole(Role role);
}
