package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import com.upc.appfinanciera.dto.ClienteDTO;

import java.util.List;

public interface IAsesorService {
    AsesorFinancieroDTO buscarAsesorPorId(Long id);
    List<AsesorFinancieroDTO> listarAsesores();
    AsesorFinancieroDTO findByEmail(String email);

}


