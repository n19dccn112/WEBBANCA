package com.n19dccn112.model.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
public class BusinessDetailDTO {
    private Long businessDetailId;
    private Date businessDateUpdate;
    private Long businessId;
    private Long productId;
}
