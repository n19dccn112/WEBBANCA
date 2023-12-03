package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByEventStatus_EventStatusId(Long eventStatusId);
    @Query(value = "SELECT EVENT_ID FROM EVENT WHERE EVENT_ID = " +
            "(SELECT MAX(EVENT_ID) FROM EVENT where EVENT_NAME = ?1);", nativeQuery = true)
    Long eventIdNewSave(String name);

    @Query(value = "select * from event where CURRENT_TIMESTAMP <= end_date", nativeQuery = true)
    List<Event> findAllEventWillGoOrHaveGoing();
}
