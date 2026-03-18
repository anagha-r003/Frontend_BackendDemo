package com.rapidrise.task2_jwt_crud_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStructure<T> {
    private int status;
    private String message;
    private T data;
}
