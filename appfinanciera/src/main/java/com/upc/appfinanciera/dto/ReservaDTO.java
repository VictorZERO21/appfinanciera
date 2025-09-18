package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private long idReserva;

    @NotNull(message = "La fecha y hora de inicio no pueden ser nulas")
    private LocalDate fechaHoraInicio;

    @NotNull(message = "La fecha y hora de fin no pueden ser nulas")
    private LocalDate fechaHoraFin;

    @NotNull(message = "El estado no puede ser nulo")
    @Size(min = 2, max = 50, message = "El estado debe tener entre 2 y 50 caracteres")
    private String estado;

    @NotNull(message = "La modalidad no puede ser nula")
    private String modalidad;

    @NotNull(message = "El DNI del cliente no puede ser nulo")
    private String dniCliente;

    @NotNull(message = "El DNI del asesor no puede ser nulo")
    private String dniAsesor;
}
