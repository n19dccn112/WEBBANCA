package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String email;
    private String password;
    private String username;
    private Long roleId;
    private String roleName;
    private String address;
    private Long userDetailIdDefault;
}
