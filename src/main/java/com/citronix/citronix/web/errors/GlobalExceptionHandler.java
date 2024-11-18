package com.citronix.citronix.web.errors;

import com.citronix.citronix.exception.Farm.FarmAlreadyExistException;
import com.citronix.citronix.exception.Farm.FarmInvalidException;
import com.citronix.citronix.exception.Farm.FarmNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException exceptions){
        Map<String,String> errors=new HashMap<>();
        exceptions.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(FarmAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handleFarmAlreadyExistException(FarmAlreadyExistException exception){
        Map<String,String> error=new HashMap<>();
        error.put("error",exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(FarmInvalidException.class)
    public ResponseEntity<Map<String,String>> handleFarmInvalidException(FarmInvalidException exception){
        Map<String,String> error=new HashMap<>();
        error.put("error",exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(FarmNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleFarmAlreadyExistException(FarmNotFoundException exception){
        Map<String,String> error=new HashMap<>();
        error.put("error",exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
