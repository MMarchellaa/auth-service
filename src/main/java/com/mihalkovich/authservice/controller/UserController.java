package com.mihalkovich.authservice.controller;

import com.mihalkovich.authservice.payload.request.SignupRequest;
import com.mihalkovich.authservice.service.UserService;
import com.mihalkovich.authservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDto getUser(Principal principal){

        return userService.getCurrentUser(principal);
    }

    @PostMapping
    public UserDto saveUser(@RequestBody SignupRequest userIn){

        return userService.createUser(userIn);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteGroup(@PathVariable String id){

        return userService.deleteUser(Long.parseLong(id));
    }

    @PutMapping
    public UserDto updateGroup(@RequestBody UserDto userDto){

        return userService.updateUser(userDto);
    }
}
