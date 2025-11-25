package com.upc.appfinanciera.excepciones;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomExceptions.NoDataAvailableException.class)
    public ResponseEntity<Object> handleNoData(CustomExceptions.NoDataAvailableException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String campo = error.getField();            // ej: "dni"
            String mensaje = error.getDefaultMessage(); // ej: "El DNI debe tener 8 dígitos."
            errores.put(campo, mensaje);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error de validación");
        body.put("type", "error");
        body.put("errors", errores);  //van  mensajes del DTO

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDatabaseConstraint(DataIntegrityViolationException ex) {
        String cause = ex.getMostSpecificCause().getMessage().toLowerCase();
        String mensaje = "Dato duplicado.";
        if (cause.contains("dni")) {
            mensaje = "El DNI ya está registrado.";
        } else if (cause.contains("cliente_email_key") || cause.contains("clien_email")) {
            mensaje = "El correo ya está registrado.";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "message", mensaje,
                        "type", "error",
                        "code", "DUPLICADO"
                )
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno: " + ex.getMessage());
    }
}
