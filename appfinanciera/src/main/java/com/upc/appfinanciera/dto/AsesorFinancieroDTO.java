package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class AsesorFinancieroDTO {
   private Long idAsesor;

    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    private String password;

    private String telefono;
    private String sobreMi;


    //@Size(max = 50, message = "La especialidad no debe superar 50 caracteres")
    //private String especialidad;
//
    //private String experiencia;
//
    @Email(message = "El correo electrónico debe ser válido")
    private String email;
}
