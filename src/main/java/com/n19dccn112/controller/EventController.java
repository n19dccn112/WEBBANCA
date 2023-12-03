package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/events")
@Tag(name = "Event")
public class EventController implements IBaseController<EventDTO, Long, EventService> {
    @Resource
    @Getter
    private EventService service;

    @GetMapping("")
    public List<EventDTO> getAll(@RequestParam(required = false) Long eventStatusId) {
        if (eventStatusId != null){
            return getService().findAllEventStatusId(eventStatusId);
        }
        return getService().findAll();
    }

    @GetMapping("/EventWillGoOrHaveGoing")
    public List<EventDTO> getAll() {
        return getService().findAllEventWillGoOrHaveGoing();
    }
}
