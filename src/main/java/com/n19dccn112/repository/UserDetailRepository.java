package com.n19dccn112.repository;

import com.n19dccn112.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    @Query(value = "SELECT USER_DETAIL_ID FROM USERS_DETAIL WHERE USER_DETAIL_ID = (SELECT MAX(USER_DETAIL_ID) FROM USERS_DETAIL where NAME = ?1);", nativeQuery = true)
    Long userDetailIdNewSave(String name);
    List<UserDetail> findAllByUser_UserId(Long userId);
}
