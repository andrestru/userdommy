package com.example.dummy.infrastructure.entry_points;

import com.example.dummy.domain.errors.BusinessException;
import com.example.dummy.domain.model.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<LoginResponse> exception(BusinessException e){
        LoginResponse entity =
                new LoginResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        return ResponseEntity.badRequest().body(entity);
    }
}
