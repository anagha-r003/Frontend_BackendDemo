package com.rapidrise.task2_jwt_crud_api.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String dateOfBirth;
}
