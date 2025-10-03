package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.TarjetaDTO;
import java.util.List;

public interface ITarjetaService {
    TarjetaDTO insertarTarjeta(TarjetaDTO tarjetaDTO);
    TarjetaDTO modificarTarjeta(TarjetaDTO tarjetaDTO);
    void eliminarTarjeta(Long id);
    TarjetaDTO buscarTarjetaPorId(Long id);
    List<TarjetaDTO> listarTarjetas();
}
