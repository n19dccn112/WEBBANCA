package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.BusinessDetail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
@Getter
@Setter
public class BusinessDTO {
    private Long businessId;
    private int desiredProfit;
}
