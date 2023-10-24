package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByEventStatus_EventStatusId(Long eventStatusId);
}
