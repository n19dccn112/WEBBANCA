package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentMethodDTO {
    private Long paymentMethodId;
    @NotNull
    @NotBlank
    private String paymentMethodName;
}
