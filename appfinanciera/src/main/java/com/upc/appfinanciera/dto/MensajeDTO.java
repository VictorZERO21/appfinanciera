package com.upc.appfinanciera.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensajeDTO {
    private Long idMensaje;
    private Long idChat;
    private String contenido;
    private String emisor;
    private LocalDateTime fechaHora;
}
