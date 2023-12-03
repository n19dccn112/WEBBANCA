package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventDTO {
    private Long eventId;
    @NotNull
    @NotBlank
    private String eventName;
    private String eventDescription;
    private Date startDate;
    private Date endDate;
    private String discountCode;
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private int discountValue;
    private Long eventStatusId;
    private List<ProductDTO> productDTOS;
}
