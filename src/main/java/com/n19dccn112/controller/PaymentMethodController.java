package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.PaymentMethodDTO;
import com.n19dccn112.service.PaymentMethodService;
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
@RequestMapping("api/paymentMethod")
@Tag(name = "PaymentMethod")
public class PaymentMethodController implements IBaseController<PaymentMethodDTO, Long, PaymentMethodService> {
    @Resource
    @Getter
    private PaymentMethodService service;

    @GetMapping("")
    public List<PaymentMethodDTO> getAll() {
        return getService().findAll();
    }
}
