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
public class ChatDTO {
    private Long idChat;
    private ClienteDTO cliente;
    private AsesorFinancieroDTO asesor;
    private LocalDateTime fechaHora;
}
