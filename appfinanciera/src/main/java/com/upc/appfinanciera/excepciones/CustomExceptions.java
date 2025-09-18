package com.upc.appfinanciera.excepciones;

public class CustomExceptions {

    // Excepción para cuando no se encuentra un Asesor
    public static class AsesorNotFoundException extends RuntimeException {
        public AsesorNotFoundException(String message) {
            super(message);
        }
    }

    // Excepción para cuando no se encuentra un Cliente
    public static class ClienteNotFoundException extends RuntimeException {
        public ClienteNotFoundException(String message) {
            super(message);
        }
    }

    // Excepción para cuando no se encuentra una Reserva
    public static class ReservaNotFoundException extends RuntimeException {
        public ReservaNotFoundException(String message) {
            super(message);
        }
    }

    // Excepción para cuando no se encuentra una Gestión Financiera
    public static class GestionFinancieraNotFoundException extends RuntimeException {
        public GestionFinancieraNotFoundException(String message) {
            super(message);
        }
    }

    // Excepción para cuando no se encuentra un Consejo
    public static class ConsejoNotFoundException extends RuntimeException {
        public ConsejoNotFoundException(String message) {
            super(message);
        }
    }

    // Excepción para cuando ocurre un error en la Calculadora
    public static class CalculadoraException extends RuntimeException {
        public CalculadoraException(String message) {
            super(message);
        }
    }
}
