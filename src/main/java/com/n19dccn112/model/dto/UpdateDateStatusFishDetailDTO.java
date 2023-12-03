package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UpdateDateStatusFishDetailDTO {
    private Long updateDateStatusFishDetailId;
    private Date updateDate;
    private Long statusFishId;
    private Long statusDetailIdFrom;
    private int amount;
}
