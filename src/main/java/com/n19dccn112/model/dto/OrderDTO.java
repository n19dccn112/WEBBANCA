package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class OrderDTO {
    private Long orderId;
    private String orderAddress;
    @Size(min = 10, max = 10)
    private String orderPhone;
    private Date orderTimeStart;
    private Date orderTimeEnd;
    private Integer paymentAmount;
    private Date paymentDate;
    private Long userId;
    private Long paymentMethodId;
    private Long orderStatusId;
    private Map<Long, Integer> reAmounts;
}
