package com.backend.vids.repository;

import com.backend.vids.entity.Folder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

  Optional<Folder> findByName(String name);

  @Query(nativeQuery = true,value = "SELECT * FROM FOLDERS WHERE ROLE_REQUIRED IN (:roles)")
  List<Folder> findAllWithRoles(@Param("roles") List<String> roleList);
}
