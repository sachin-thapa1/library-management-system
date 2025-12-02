package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?>register(@Valid @RequestBody RegisterRequest request) {
        try {
            String message = authService.register(request);
            Map<String, String> response = new HashMap  <>();
            response.put("message", message);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(RuntimeException e) {
            Map<String, String> error = new HashMap <>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Login-section
    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody LoginRequest  request) {
        try{
            String token = authService.login(request);
            Map<String, String> response = new HashMap <>();
            response.put("token", token);
            response.put("message", "Login successfull!");
            return ResponseEntity.ok(response);
        } catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    // Register admin endpoint
@PostMapping("/register-admin")
public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest request) {
    try {
        String message = authService.registerAdmin(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (RuntimeException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
}
