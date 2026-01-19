package ch.zhaw.ssdd.cleanboot.adapter.configuration.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        // Return 400 Bad Request with the exception message
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("INTERNAL DOMAIN ERROR: " + ex.getMessage());
    }
}