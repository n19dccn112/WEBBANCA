package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class FeatureTypeDTO {
    private Long featureTypeId;
    @NotNull
    private String featureTypeName;
    private String featureTypeUnit;
    private String isShow;
}
