package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class FeatureDetailDTO {
    private Long featureDetailId;
    private Long unitDetailId;
    private Long featureId;
    private Integer featurePrice;
}
