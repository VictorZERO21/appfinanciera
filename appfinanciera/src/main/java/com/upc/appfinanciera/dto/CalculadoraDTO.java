package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculadoraDTO {
    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 1, message = "El monto debe ser mayor a cero")
    private long monto;

    @NotNull(message = "Las cuotas no pueden ser nulas")
    @Min(value = 1, message = "El número de cuotas debe ser mayor a cero")
    private long cuotas;

    @NotNull(message = "La tasa de interés no puede ser nula")
    @Min(value = 0, message = "La tasa de interés no puede ser negativa")
    private long tasaInteres;
}
