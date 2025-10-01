package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ChatDTO;

import java.util.List;


public interface IChatServicie {
    ChatDTO insertar(ChatDTO chatDTO);
    List<ChatDTO> listar();
    ChatDTO modificar(ChatDTO chatDTO);
    void eliminar(Long id);
    List<ChatDTO> listarPorClienteYAsesor(Long idCliente, Long idAsesor);
    List<ChatDTO> listarPorAsesor(Long idAsesor);
    List<Long> listarClientesDeAsesor(Long idAsesor);

}
