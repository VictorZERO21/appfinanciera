package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.Max;
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
public class CalificacionAsesorDTO {
    private Long idCalificacion;

    @NotNull(message = "El id del asesor no puede ser nulo")
    private Long idAsesor;

    @NotNull(message = "El id del cliente no puede ser nulo")
    private Long idCliente;

    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private int puntuacion;

    @NotNull(message = "Debe agregar al menos un comentario corto")
    private String comentario;
}
