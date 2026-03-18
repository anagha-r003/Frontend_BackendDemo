package com.rapidrise.task2_jwt_crud_api.service;

import com.rapidrise.task2_jwt_crud_api.dto.AuthResponse;
import com.rapidrise.task2_jwt_crud_api.dto.LoginRequest;
import com.rapidrise.task2_jwt_crud_api.dto.RegisterRequest;
import com.rapidrise.task2_jwt_crud_api.dto.ResponseStructure;
import com.rapidrise.task2_jwt_crud_api.entity.User;
import com.rapidrise.task2_jwt_crud_api.exception.InvalidCredentialsException;
import com.rapidrise.task2_jwt_crud_api.exception.PasswordMismatchException;
import com.rapidrise.task2_jwt_crud_api.exception.UserAlreadyExistsException;
import com.rapidrise.task2_jwt_crud_api.jwt.JwtService;
import com.rapidrise.task2_jwt_crud_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseStructure<String>> register(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDateOfBirth())
                .build();

        userRepository.save(user);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(201);
        response.setMessage("User registered");
        response.setData(user.getEmail());

        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ResponseStructure<AuthResponse>> login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateAccessToken(user.getEmail());

        AuthResponse authResponse = new AuthResponse(token);

        ResponseStructure<AuthResponse> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMessage("Login successful");
        response.setData(authResponse);

        return ResponseEntity.ok(response);
    }
}
