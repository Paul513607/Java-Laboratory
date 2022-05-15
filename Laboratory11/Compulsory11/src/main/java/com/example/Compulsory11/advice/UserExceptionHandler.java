package com.example.Compulsory11.advice;

import com.example.Compulsory11.customexception.UserAlreadyExists;
import com.example.Compulsory11.customexception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            UserNotFoundException ex) {
        String msg = "User not found!";
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserAlreadyExists.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            UserAlreadyExists ex) {
        String msg = "User already exists!";
        return ResponseEntity.notFound().build();
    }
}
