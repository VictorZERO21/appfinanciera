package com.upc.appfinanciera.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long idReserva;

    @NotNull(message = "La fecha y hora de inicio no pueden ser nulas")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha y hora de fin no pueden ser nulas")
    private LocalDateTime fechaHoraFin;

    @NotNull(message = "El estado no puede ser nulo")
    @Size(min = 2, max = 50, message = "El estado debe tener entre 2 y 50 caracteres")
    private String estado;

    private String modalidad;

    @NotNull(message = "El cliente es obligatorio")
    @Valid
    private ClienteDTO cliente;

    @NotNull(message = "El asesor es obligatorio")
    @Valid
    private AsesorFinancieroDTO asesor;

    @NotNull(message = "El pago es obligatorio")
    @Valid
    private PagoDTO pago;
}
