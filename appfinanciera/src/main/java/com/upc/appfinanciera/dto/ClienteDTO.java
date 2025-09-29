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
public class ClienteDTO {
    private Long idCliente;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 12, message = "El DNI debe tener entre 8 y 12 caracteres")
    private String dni;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    private String password;


    @Email(message = "El correo electrónico debe ser válido")
    private String email;
    private String sobreMi;


    @Size(max = 9, message = "El teléfono no debe superar los 9 dígitos")
    private String telefono;

}
