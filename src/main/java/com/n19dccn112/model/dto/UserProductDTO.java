package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserProductDTO {
    private Long userProductId;
    private Long productId;
    private Long userId;
    private Long idUserProductReply;
    private String comment;
    @DecimalMin(value = "1")
    @DecimalMax(value = "5")
    private Integer point;
    private String isLove;
    private String isSeen;
    private String phone;
}
