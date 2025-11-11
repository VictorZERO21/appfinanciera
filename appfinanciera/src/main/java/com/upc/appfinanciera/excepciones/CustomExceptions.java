package com.upc.appfinanciera.excepciones;

public class CustomExceptions {

    public static class AsesorNotFoundException extends RuntimeException {
        public AsesorNotFoundException(String message) {
            super(message);
        }
    }

    public static class ClienteNotFoundException extends RuntimeException {
        public ClienteNotFoundException(String message) {
            super(message);
        }
    }

    public static class ReservaNotFoundException extends RuntimeException {
        public ReservaNotFoundException(String message) {
            super(message);
        }
    }

    public static class PagoNotFoundException extends RuntimeException {
        public PagoNotFoundException(String message) {
            super(message);
        }
    }

    public static class DisponibilidadNotFoundException extends RuntimeException {
        public DisponibilidadNotFoundException(String message) {
            super(message);
        }
    }

    public static class GestionFinancieraNotFoundException extends RuntimeException {
        public GestionFinancieraNotFoundException(String message) {
            super(message);
        }
    }
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
    public static class NoDataAvailableException extends RuntimeException {
        public NoDataAvailableException(String message) {
            super(message);
        }
    }
}
