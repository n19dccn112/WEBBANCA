package com.n19dccn112.repository;

import com.n19dccn112.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findAllByUsername(String user);
    @Query(value = "Select count(*) from users where username = ?1", nativeQuery = true)
    Integer existsByUsername(String username);

    @Query(value = "Select count(*) from USERS_DETAIL where PHONE like ?1", nativeQuery = true)
    Integer existsByPhone(String phone);

    @Query(value = "Select count(*) from users where EMAIL like ?1", nativeQuery = true)
    Integer existsByEmail(String email);
    Optional <User> findByEmail(String email);

    @Query(value = "SELECT USER_ID FROM users WHERE USER_ID = (SELECT MAX(USER_ID) FROM users where USERNAME = ?1);", nativeQuery = true)
    Long userIdNewSave(String name);
}
