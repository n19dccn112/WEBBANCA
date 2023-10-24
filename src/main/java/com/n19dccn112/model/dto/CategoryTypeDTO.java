package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CategoryTypeDTO {
    private Long categoryTypeId;
    @NotNull
    private String categoryTypeName;
    private String categoryTypeDescription;
}
