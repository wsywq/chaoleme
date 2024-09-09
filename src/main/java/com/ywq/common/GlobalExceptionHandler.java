package com.ywq.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseTemplate<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.info("Exception handler: {}", ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] s = ex.getMessage().split(" ");
            String split = s[2] + "already exists";
            return ResponseTemplate.error(split);
        }
        return ResponseTemplate.error("Unknown error");
    }

    @ExceptionHandler(CustomException.class)
    public ResponseTemplate<String> customExceptionHandler(CustomException ex) {
        log.info("Custom Exception handler {}", ex.getMessage());
        return ResponseTemplate.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseTemplate> handleExceptionDefined(Exception e){
        return new ResponseEntity<>(ResponseTemplate.error("Test"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
