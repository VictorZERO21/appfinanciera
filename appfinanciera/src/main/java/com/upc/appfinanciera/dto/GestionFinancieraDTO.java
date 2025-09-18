package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestionFinancieraDTO {
    private long idGestion;

    @NotNull(message = "El título no puede ser nulo")
    private String titulo;

    @NotNull(message = "El tipo no puede ser nulo")
    private String tipo;

    @Min(value = 0, message = "El monto no puede ser negativo")
    private double monto;

    private LocalDate fecha;

    @NotNull(message = "El DNI del cliente no puede ser nulo")
    private String dniCliente;
}
