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
    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer puntuacion;
    private String comentario;
    @NotNull(message = "El asesor es obligatorio")
    private Long idAsesor;
    @NotNull(message = "El cliente es obligatorio")
    private Long idCliente;
}
