package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;

@Getter
@Setter
public class OrderDetailDTO {
    private Long orderDetailId;
    private Long orderId;
    private Long unitDetailId;
    @DecimalMin(value = "1")
    private int amount;
}
