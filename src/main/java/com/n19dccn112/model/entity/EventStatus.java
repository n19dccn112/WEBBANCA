package com.n19dccn112.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EVENT_STATUS")
public class EventStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_STATUS_ID")
    private Long eventStatusId;

    @Column(name = "EVENT_STATUS_NAME")
    private String eventStatusName;

    @OneToMany(mappedBy = "eventStatus")
    private List<Event> events;
}
