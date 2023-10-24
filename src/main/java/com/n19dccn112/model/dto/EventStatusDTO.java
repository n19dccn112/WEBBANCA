package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventStatusDTO {
    private Long eventStatusId;
    private String eventStatusName;
}
