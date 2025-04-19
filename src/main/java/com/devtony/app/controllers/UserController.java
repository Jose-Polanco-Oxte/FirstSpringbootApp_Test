package com.devtony.app.controllers;

import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.dto.user.UserRequestDto;
import com.devtony.app.dto.user.UserResponseDto;
import com.devtony.app.repository.projections.PredictedUserProjection;
import com.devtony.app.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUser());
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/edit-me")
    public ResponseEntity<SimpleFormatBodyResponse> editMer(@RequestBody UserRequestDto userRequestDto) {
        userService.updateUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SimpleFormatBodyResponse("Usuario actualizado correctamente"));
    }

    @PreAuthorize("permitAll()")
    @DeleteMapping("/delete-me")
    public ResponseEntity<SimpleFormatBodyResponse> deleteMe() {
        return null;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search-users")
    public ResponseEntity<List<PredictedUserProjection>> searchUserByPrediction(
            @RequestParam String searchingText,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.predictionUsers(searchingText, limit));
    }
}