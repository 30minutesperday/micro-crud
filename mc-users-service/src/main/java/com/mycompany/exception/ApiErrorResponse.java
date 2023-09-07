package com.mycompany.exception;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private final String uuid;
    private final String errorCode;
    private final String message;
    private final String path;
}
