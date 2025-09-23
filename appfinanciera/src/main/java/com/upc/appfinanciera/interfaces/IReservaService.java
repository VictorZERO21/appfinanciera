package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ReservaDTO;
import java.util.List;

public interface IReservaService {
    ReservaDTO insertarReserva(ReservaDTO reservaDTO);
    ReservaDTO modificarReserva(ReservaDTO reservaDTO);
    void eliminarReserva(Long id);
    ReservaDTO buscarReservaPorId(Long id);
    List<ReservaDTO> listarReservas();
    List<ReservaDTO> listarReservasPorClienteId(Long idCliente);
    List<ReservaDTO> listarReservasPorClienteDni(String dniCliente);
    List<ReservaDTO> listarReservasPorAsesorId(Long idAsesor);
    List<ReservaDTO> listarReservasPorAsesorDni(String dniAsesor);
}
