package com.devtony.app.controllers;

import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.repository.projections.UserProjection;
import com.devtony.app.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PreAuthorize("permitAll()")
    @GetMapping("/get-all")
    public ResponseEntity<List<UserProjection>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/edit-user")
    public ResponseEntity<SimpleFormatBodyResponse> editUser(@RequestBody UserRequestDto userRequestDto) {
        userService.updateUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new SimpleFormatBodyResponse("Usuario actualizado correctamente"));
    }

    //Test
    @GetMapping("/test1/{name}")
    public String test2(@PathVariable String name){
        return "Test complete " + name;
    }
}