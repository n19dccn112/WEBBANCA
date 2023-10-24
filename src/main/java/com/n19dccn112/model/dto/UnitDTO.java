package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UnitDTO {
    private Long unitId;
    @NotNull
    @NotBlank
    private String unitName;
    private String unitDescription;
}
