package com.example.project2.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

@ControllerAdvice
public class customExceptionHandler {
    @ExceptionHandler(OtherException.class)
    public String handlerNotFoundException(OtherException exception, Model model){
        model.addAttribute("error", exception);
        return "foo";
    }
}