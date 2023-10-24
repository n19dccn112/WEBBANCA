package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.District;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class WardDTO {
    private Integer wardsId;
    private String wardsName;
    private Integer districtsId;
}
