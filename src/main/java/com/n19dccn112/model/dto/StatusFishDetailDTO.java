package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class StatusFishDetailDTO {
    private Long statusFishDetailId;
    private Long statusFishId;
    private Long unitDetailId;
    private int amount;
    private Date dateUpdate;
}
