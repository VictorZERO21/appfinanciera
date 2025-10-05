package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadDTO {
    private Long idDisponibilidad;
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate fecha;
    @NotNull(message = "La hora de inicio no puede ser nula")
    private LocalTime horaInicio;
    @NotNull(message = "La hora de fin no puede ser nula")
    private LocalTime horaFin;
    @NotNull(message = "El campo disponible no puede ser nulo")
    private Boolean disponible;
    @NotNull(message = "El id del asesor no puede ser nulo")
    private Long idAsesor;
}
