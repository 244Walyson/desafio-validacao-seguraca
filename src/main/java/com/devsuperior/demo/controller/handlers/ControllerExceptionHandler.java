package com.devsuperior.demo.controller.handlers;

import com.devsuperior.demo.dto.CustomError;
import com.devsuperior.demo.dto.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Timespan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(), status.value(),"dados invalidos", request.getRequestURI());
        for (FieldError field : e.getBindingResult().getFieldErrors()){
            error.addError(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}
