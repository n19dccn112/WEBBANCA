package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StatusFishDTO {
    private Long statusFishId;
    @NotNull
    @NotBlank
    private String statusFishName;
}
