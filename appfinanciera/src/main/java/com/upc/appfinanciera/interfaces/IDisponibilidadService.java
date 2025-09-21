package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.DisponibilidadDTO;

import java.util.List;

public interface IDisponibilidadService {
    DisponibilidadDTO insertar(DisponibilidadDTO disponibilidadDto);
    List<DisponibilidadDTO> buscarTodos();
    List<DisponibilidadDTO> buscarPorAsesor(Long idAsesor);
    void eliminar(Long idDisponibilidad);

    DisponibilidadDTO actualizar(DisponibilidadDTO disponibilidadDto);
}
