package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ChatDTO;

import java.util.List;


public interface IChatServicie {
    ChatDTO insertar(ChatDTO chatDTO);
    List<ChatDTO> listar();
    ChatDTO modificar(ChatDTO chatDTO);
    void eliminar(Long id);

}
