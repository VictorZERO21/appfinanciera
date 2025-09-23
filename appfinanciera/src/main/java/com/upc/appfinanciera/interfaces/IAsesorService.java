package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import java.util.List;

public interface IAsesorService {
    AsesorFinancieroDTO insertarAsesor(AsesorFinancieroDTO asesorDTO);
    AsesorFinancieroDTO modificarAsesor(AsesorFinancieroDTO asesorDTO);
    void eliminarAsesor(Long id);
    AsesorFinancieroDTO buscarAsesorPorId(Long id);
    AsesorFinancieroDTO buscarAsesorPorDni(String dni);
    AsesorFinancieroDTO buscarAsesorPorEmail(String email);
    List<AsesorFinancieroDTO> listarAsesores();
    List<AsesorFinancieroDTO> buscarAsesoresPorNombre(String nombre);
    List<AsesorFinancieroDTO> buscarAsesoresPorEspecialidad(String especialidad);
}

