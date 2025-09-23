package com.upc.appfinanciera.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Long idPago;

    @NotBlank(message = "El nombre del titular no puede estar vacío")
    private String nombreTarjeta;

    @NotBlank(message = "El número de tarjeta no puede estar vacío")
    @Pattern(regexp = "\\d{13,19}", message = "El número de tarjeta debe tener entre 13 y 19 dígitos")
    private String numeroTarjeta;

    @Min(value = 1, message = "El mes de expiración debe estar entre 1 y 12")
    @Max(value = 12, message = "El mes de expiración debe estar entre 1 y 12")
    private int mesExpiracion;

    @Min(value = 2025, message = "El año de expiración debe ser mayor o igual al actual")
    private int anioExpiracion;

    @NotBlank(message = "El CVC no puede estar vacío")
    @Pattern(regexp = "\\d{3,4}", message = "El CVC debe tener 3 o 4 dígitos")
    private String cvc;

    private double montoTotal;
}
