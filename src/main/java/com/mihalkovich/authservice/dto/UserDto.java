package com.mihalkovich.authservice.dto;

import com.mihalkovich.authservice.entity.enums.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Role role;
}
