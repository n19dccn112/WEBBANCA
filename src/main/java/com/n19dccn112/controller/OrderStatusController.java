package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.dto.OrderStatusDTO;
import com.n19dccn112.service.EventStatusService;
import com.n19dccn112.service.OrderStatusService;
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
@RequestMapping("api/orderStatus")
@Tag(name = "OrderStatus")
public class OrderStatusController implements IBaseController<OrderStatusDTO, Long, OrderStatusService> {
    @Resource
    @Getter
    private OrderStatusService service;

    @GetMapping("")
    public List<OrderStatusDTO> getAll() {
        return getService().findAll();
    }
}
