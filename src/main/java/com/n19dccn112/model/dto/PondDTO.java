package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class PondDTO {
    private Long pondId;
    private Integer standardPrice;
    private Integer pondAmount;
    private Integer songAmount;
    private Integer chetAmount;
    private Integer benhAmount;
    private Date inputDate;
    private int priceShip;
    private Long unitDetailId;
    private int priceStand;
}
