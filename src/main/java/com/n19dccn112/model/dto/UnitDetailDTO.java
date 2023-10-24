package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UnitDetailDTO {
    private Long unitDetailId;
    private Long unitId;
    private Long productId;
    private float speedGrowth;
    private float length;
    private Integer productPrice;
    private String unitUnitPrice;
    private Integer unitDetailAmount;
}
