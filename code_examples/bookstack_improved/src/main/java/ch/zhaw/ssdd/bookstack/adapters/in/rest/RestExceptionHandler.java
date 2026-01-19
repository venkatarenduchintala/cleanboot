package ch.zhaw.ssdd.bookstack.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ch.zhaw.ssdd.bookstack.domain.exception.DomainValidationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
        IllegalArgumentException.class,  
        DomainValidationException.class})
    public ResponseEntity<String> handleBadRequestExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}