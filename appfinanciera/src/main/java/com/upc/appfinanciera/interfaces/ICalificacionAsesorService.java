package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.CalificacionAsesorDTO;

import java.util.List;

public interface ICalificacionAsesorService {
    CalificacionAsesorDTO insertar(CalificacionAsesorDTO dto);

    List<CalificacionAsesorDTO> listarPorAsesor(Long idAsesor);
}
