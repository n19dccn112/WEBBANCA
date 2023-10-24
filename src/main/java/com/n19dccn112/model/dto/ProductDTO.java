package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.UnitDetail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ProductDTO {
    private Long productId;
    private Date importDate;
    private String productDescription;
    @NotNull
    @NotBlank
    private String productName;
    private Date updateDateProduct;
    private Date expirationDate;
    private String isAnimal;
    private List<String> categoryNames;
    private List<String> images;
    private int amountProduct;
    private int minPrice;
    private int maxPrice;
}
