package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.service.EventStatusService;
import com.n19dccn112.service.OrderDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orderDetails")
@Tag(name = "OrderDetail")
public class OrderDetailController implements IBaseController<OrderDetailDTO, Long, OrderDetailService> {
    @Resource
    @Getter
    private OrderDetailService service;

    @GetMapping("")
    public List<OrderDetailDTO> getAll(@RequestParam(required = false) Long unitDetailId,
                                       @RequestParam(required = false) Long orderId) {
        if (unitDetailId == null && orderId != null){
            return getService().findAllOrderId(orderId);
        }
        if (unitDetailId != null && orderId == null){
            return getService().findAllUnitDetail(unitDetailId);
        }
        if (unitDetailId != null && orderId != null){
            return getService().findAlOrderIdAndUnitDetailId(orderId, unitDetailId);
        }
        return getService().findAll();
    }
}
