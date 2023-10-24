package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT ROLE_ID FROM role WHERE ROLE_NAME LIKE ?1", nativeQuery = true)
    List<Long> existsByRoleName(String roleName);
}
