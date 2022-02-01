package com.mihalkovich.authservice.mapper;

import com.mihalkovich.authservice.dto.UserDto;
import com.mihalkovich.authservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toDto(User user);
}
