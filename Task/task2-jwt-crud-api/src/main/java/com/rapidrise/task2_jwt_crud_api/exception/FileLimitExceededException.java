package com.rapidrise.task2_jwt_crud_api.exception;

public class FileLimitExceededException extends RuntimeException{
    public FileLimitExceededException(String message) {
        super(message);
    }
}
