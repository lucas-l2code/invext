package com.invext.invext.config;

import com.invext.invext.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex) {
        ArrayList<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(erros));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ArrayList<String> erros = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.add(fieldName + ": " + errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(erros));
    }

    private Map<String, Object> buildErrorResponse(List<String> erros) {
        Map<String, Object> response = new HashMap<>();
        response.put("erros", erros);
        return response;
    }

}
