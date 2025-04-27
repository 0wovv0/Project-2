package com.example.project2.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class myResponseException {
    @ExceptionHandler(ResponseException.class)
    public String myResponseException(ResponseException exception){
        return exception.getMessage();
    }
}
