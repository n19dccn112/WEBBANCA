package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orders")
@Tag(name = "Order")
public class OrderController implements IBaseController<OrderDTO, Long, OrderService> {
    @Resource
    @Getter
    private OrderService service;

    @GetMapping("")
    public List<OrderDTO> getAll(@RequestParam(required = false) Long userId,
                                 @RequestParam(required = false) Long paymentMethodId,
                                 @RequestParam(required = false) Long orderStatusId) {
        if (paymentMethodId != null && orderStatusId == null && userId == null){
            return getService().findAllByPaymentMethodId(paymentMethodId);
        }
        if (userId != null && paymentMethodId == null && orderStatusId == null){
            return getService().findAllByUserId(userId);
        }
        if (orderStatusId != null && paymentMethodId == null && userId == null){
            return getService().findAllByOrderStatusId(orderStatusId);
        }
        if (paymentMethodId != null && orderStatusId != null && userId == null){
            return getService().findAllByPaymentMethodIdAndOrderStatusId(paymentMethodId, orderStatusId);
        }
        if (userId != null && paymentMethodId != null && orderStatusId == null){
            return getService().findAllByUserIdAndPaymentMethodId(userId, paymentMethodId);
        }
        if (userId != null && paymentMethodId == null){
            return getService().findAllByUserIdAndOrderStatusId(userId, orderStatusId);
        }
        if (userId != null){
            return getService().findAllByUserIdAndPaymentMethodIdAndOrderStatusId(userId, paymentMethodId, orderStatusId);
        }
        return getService().findAll();
    }
    @GetMapping("/phone")
    public OrderDTO get1Name(@RequestParam(required = true) String phone) {
        return getService().findByOrderIdNewSave(phone);
    }
    @GetMapping("/bcdt")
    public List<OrderDTO> bCDT(@RequestParam(required = true)String dateFrom,
                               @RequestParam(required = true) String dateTo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dayTo = dateFormat.parse(dateTo);
            Date dayFrom = dateFormat.parse(dateFrom);
            System.out.println(dayTo);
            return getService().bCDT(dayTo, dayFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/shipSuccess")
    public ResponseEntity<?> shipSuccess(@RequestParam(required = true) Long orderId) {
        return getService().shipSuccess(orderId);
    }
    @GetMapping("/shipCancel")
    public ResponseEntity<?> shipCancel(@RequestParam(required = true) Long orderId) {
        return getService().shipCancel(orderId);
    }
}
