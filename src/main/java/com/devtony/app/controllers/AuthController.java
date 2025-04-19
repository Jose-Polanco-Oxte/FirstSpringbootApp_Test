package com.devtony.app.controllers;

import com.devtony.app.dto.login.LoginRequestDto;
import com.devtony.app.dto.login.LoginResponseDto;
import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.services.interfaces.IAuthService;
import com.devtony.app.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private final IUserService userService;
    private final IAuthService authService;

    public AuthController(IUserService userService, IAuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody  LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleFormatBodyResponse> register(@Valid @RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleFormatBodyResponse("Usuario registrado correctamente"));
    }
}