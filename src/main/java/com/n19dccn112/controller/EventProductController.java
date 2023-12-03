package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.service.CategoryService;
import com.n19dccn112.service.EventProductService;
import com.n19dccn112.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/eventProducts")
@Tag(name = "EventProduct")
public class EventProductController implements IBaseController<EventProductDTO, Long, EventProductService> {
    @Resource
    @Getter
    private EventProductService service;

    @GetMapping("")
    public List<EventProductDTO> getAll(@RequestParam(required = false) Long productId, 
                                        @RequestParam(required = false) Long eventId,
                                        @RequestParam(required = false) Long productIdMaxEvent) {
            if (productId == null && eventId != null){
                return getService().findAllEventId(eventId);
            }
            if (productId != null && eventId == null){
                return getService().findAllProductId(productId);
            }
            if (productId != null && eventId != null){
                return getService().findAllEventIdAndProductId(eventId, productId);
            }
            if (productIdMaxEvent != null){
                return getService().findAllProductIdAndMaxEvent(productIdMaxEvent);
            }
            return getService().findAll();
        }
}

