package com.devtony.app.controllers;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserAuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/init")
public class InitController {

    private final Map<String, String> response = new HashMap<>();

    @GetMapping("/connection/{key}")
    public ResponseEntity<Map<String, String>> connectionEstablished(@PathVariable Long key) throws UserAuthException {
        response.clear();
        if (key == 2005){
            response.put("status", "success");
            response.put("message", "conexión establecida");
        } else{
            throw new UserAuthException("Acces deined",
                    new ExceptionDetails("Acceso no autorizado", "hight"));
        }
        return ResponseEntity.ok(response);
    }
}