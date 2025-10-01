package com.upc.appfinanciera.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private Long idChat;
    private Long idCliente;
    private Long idAsesor;
    private String comentario;
    private LocalDateTime fechaHora;
}
