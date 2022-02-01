package com.mihalkovich.authservice.controller;

import com.mihalkovich.authservice.payload.request.LoginRequest;
import com.mihalkovich.authservice.payload.request.SignupRequest;
import com.mihalkovich.authservice.payload.response.JWTTokenSuccessResponse;
import com.mihalkovich.authservice.payload.response.MessageResponse;
import com.mihalkovich.authservice.service.AuthService;
import com.mihalkovich.authservice.service.UserService;
import com.mihalkovich.authservice.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public AuthController(UserService userService, AuthService authService, ResponseErrorValidation responseErrorValidation) {
        this.userService = userService;
        this.authService = authService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        System.out.println("method");
        String jwt = authService.jwtGenerator(loginRequest);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody SignupRequest signupRequest) {
        System.out.println("method");

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
