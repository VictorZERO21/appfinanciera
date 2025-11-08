package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.GestionFinancieraDTO;
import java.time.LocalDate;
import java.util.List;

public interface IGestionFinancieraService {
    public GestionFinancieraDTO insertar(GestionFinancieraDTO gestionFinanciera);
    public List<Object[]> reportePorTipo(String tipo);
    public List<Object[]> reportePorFecha(LocalDate fecha);
    List<GestionFinancieraDTO> listarId(Long id);
    List<GestionFinancieraDTO> listar();
}
