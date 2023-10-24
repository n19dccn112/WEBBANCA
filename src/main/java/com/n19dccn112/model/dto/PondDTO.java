package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PondDTO {
    private Long pondId;
    @NotBlank
    @NotNull
    private String pondName;
    private Integer pondArea;
    private Integer standardPrice;
    private Integer pondAmount;
    private Long unitDetailId;
    private Long statusFishId;
}
