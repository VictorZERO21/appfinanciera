package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import java.util.List;

public interface IAsesorService {
    public AsesorFinancieroDTO insertar(AsesorFinancieroDTO asesor);
    public AsesorFinancieroDTO actualizar(AsesorFinancieroDTO asesor);
    public void eliminar(String dni);
    public AsesorFinancieroDTO buscarPorDni(String dni);
    public List<AsesorFinancieroDTO> buscarTodos();
}
