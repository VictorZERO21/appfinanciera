package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.dto.ClienteDTO;

import java.util.List;

public interface IReservaService {
    ReservaDTO insertarReserva(ReservaDTO reservaDTO);
    ReservaDTO modificarReserva(ReservaDTO reservaDTO);
    void eliminarReserva(Long id);
    ReservaDTO buscarReservaPorId(Long id);
    List<ReservaDTO> listarReservas();
    List<ReservaDTO> listarReservasPorClienteId(Long idCliente);
    List<ReservaDTO> listarReservasPorAsesorId(Long idAsesor);
    List<ClienteDTO> listarClientesConReservas();
}


