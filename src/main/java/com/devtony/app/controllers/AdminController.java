package com.devtony.app.controllers;


import com.devtony.app.dto.admin.UserAdminRequestDto;
import com.devtony.app.dto.admin.UserAdminResponseDto;
import com.devtony.app.exception.AdminException;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.repository.projections.UserProjection;
import com.devtony.app.services.interfaces.IAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final int adminKey;

    public AdminController(@Value("${admin.key}") int adminKey) {
        this.adminKey = adminKey;
    }

    @Autowired
    private IAdminService adminService;

    @PostMapping("/change-user-profile/{adminKey}")
    public ResponseEntity<UserAdminResponseDto> changeUserProfile(@PathVariable int adminKey, @Valid @RequestBody UserAdminRequestDto userRequestDto) {
        if (!(adminKey == this.adminKey)) {
            throw new AdminException("Invalid admin key",
                    new ExceptionDetails("Clave de administrador inválida", "high"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(adminService.changeUserProfile(userRequestDto));
    }

    @GetMapping("/get-users/{adminKey}")
    public ResponseEntity<List<UserProjection>> getUsers(@PathVariable int adminKey) {
        if (!(adminKey == this.adminKey)) {
            throw new AdminException("Invalid admin key",
                    new ExceptionDetails("Clave de administrador inválida", "high"));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(adminService.getUsers());
    }

}
