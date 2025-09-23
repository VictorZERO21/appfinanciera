package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.PagoDTO;
import java.util.List;

public interface IPagoService {
    PagoDTO insertarPago(PagoDTO pagoDTO);
    PagoDTO modificarPago(PagoDTO pagoDTO);
    void eliminarPago(Long id);
    PagoDTO buscarPagoPorId(Long id);
    List<PagoDTO> listarPagos();
}
