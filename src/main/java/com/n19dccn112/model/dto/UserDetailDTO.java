package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDetailDTO {
    private Long userDetailId;
    private String address;
    @NotNull
    @NotBlank
    private String name;
    private Long userId;
    private Integer provinceId;
    private Integer districtId;
    private Integer wardId;
    private String provinceName;
    private String districtName;
    private String wardName;
    private String phone;
}
