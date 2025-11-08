package com.upc.appfinanciera.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            CustomExceptions.AsesorNotFoundException.class,
            CustomExceptions.ClienteNotFoundException.class,
            CustomExceptions.ReservaNotFoundException.class,
            CustomExceptions.PagoNotFoundException.class,
            CustomExceptions.DisponibilidadNotFoundException.class,
            CustomExceptions.GestionFinancieraNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ValidationException.class)
    public ResponseEntity<Object> handleValidation(CustomExceptions.ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno: " + ex.getMessage());
    }
}
