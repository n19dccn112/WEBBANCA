package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDTO {
    private Long categoryId;
    @NotNull
    private String categoryName;
    private String categoryDescription;
    private Long categoryTypeId;
    private Integer amountProducts;
}
