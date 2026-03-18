package com.rapidrise.task2_jwt_crud_api.controller;

import com.rapidrise.task2_jwt_crud_api.dto.LoginRequest;
import com.rapidrise.task2_jwt_crud_api.dto.RegisterRequest;
import com.rapidrise.task2_jwt_crud_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
