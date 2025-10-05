package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import java.util.List;

public interface IAsesorService {
    AsesorFinancieroDTO buscarAsesorPorId(Long id);
    List<AsesorFinancieroDTO> listarAsesores();
}


