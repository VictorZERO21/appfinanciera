package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsejoDTO {
    private long idConsejo;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    private String titulo;

    private String contenido;

    @NotNull(message = "El DNI del cliente no puede ser nulo")
    private String dniCliente;

    private String dniAsesor;
}
