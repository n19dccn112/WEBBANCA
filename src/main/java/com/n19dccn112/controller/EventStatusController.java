package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.service.EventService;
import com.n19dccn112.service.EventStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/eventStatus")
@Tag(name = "EventStatus")
public class EventStatusController implements IBaseController<EventStatusDTO, Long, EventStatusService> {
    @Resource
    @Getter
    private EventStatusService service;

    @GetMapping("")
    public List<EventStatusDTO> getAll() {
        return getService().findAll();
    }
}


