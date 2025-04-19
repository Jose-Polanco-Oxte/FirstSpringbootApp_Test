package com.devtony.app.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;

import java.io.File;


@RestController
@RequestMapping("/qr")
public class QRController {

    @GetMapping("/get-qr")
    public ResponseEntity<Resource> getQR(
            @RequestParam(defaultValue = "NaN") String email
    ) {
        File file = new File("src/UsersFiles/" + email + ".png");
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
