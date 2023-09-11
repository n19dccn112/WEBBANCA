package n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FeatureDTO {
    private Long featureId;
    @DecimalMin(value = "0")
    private int point;
    private String specific;
    @NotNull
    private Long featureTypeId;
    private String featureTypeName;
    private String unit;
}
