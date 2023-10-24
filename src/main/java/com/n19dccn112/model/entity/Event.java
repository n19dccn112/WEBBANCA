package com.n19dccn112.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "EVENT_DESCRIPTION")
    private String eventDescription;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "DISCOUNT_CODE")
    private String discountCode;

    @Column(name = "DISCOUNT_VALUE")
    private int discountValue;

    @Column(name = "IS_SHOW_EVENT")
    private String isShow;

    @OneToMany(mappedBy = "event")
    private List<EventProduct> eventProducts;

    @ManyToOne
    @JoinColumn(name = "EVENT_STATUS_ID")
    private EventStatus eventStatus;
}
