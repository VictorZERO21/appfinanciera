package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ReservaDTO;
import java.util.List;

public interface IReservaService {
    public ReservaDTO insertar(ReservaDTO reserva);
    public ReservaDTO actualizar(ReservaDTO reserva);
    public void eliminar(Long id);
    public List<ReservaDTO> buscarPorCliente(String dniCliente);
    public List<ReservaDTO> buscarTodos();
}
