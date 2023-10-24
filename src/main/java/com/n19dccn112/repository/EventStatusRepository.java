package com.n19dccn112.repository;

import com.n19dccn112.model.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
