package com.rapidrise.task2_jwt_crud_api.exception;

import com.rapidrise.task2_jwt_crud_api.dto.ResponseStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ResponseStructure<String>> handlePasswordMismatch(PasswordMismatchException ex){

        logger.error(ex.getMessage(), ex);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserExists(UserAlreadyExistsException ex){

        logger.error(ex.getMessage(), ex);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidCredentials(InvalidCredentialsException ex){

        logger.error(ex.getMessage(), ex);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FileLimitExceededException.class)
    public ResponseEntity<ResponseStructure<String>> handleFileLimitExceeded(FileLimitExceededException ex){

        logger.error(ex.getMessage(), ex);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
