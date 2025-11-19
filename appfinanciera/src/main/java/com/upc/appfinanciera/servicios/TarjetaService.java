package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.TarjetaDTO;
import com.upc.appfinanciera.entidades.Tarjeta;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.interfaces.ITarjetaService;
import com.upc.appfinanciera.repositorios.TarjetaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarjetaService implements ITarjetaService {

    @Autowired
    private TarjetaRepositorio tarjetaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TarjetaDTO insertarTarjeta(TarjetaDTO tarjetaDTO) {
        Tarjeta tarjeta = modelMapper.map(tarjetaDTO, Tarjeta.class);
        return modelMapper.map(tarjetaRepositorio.save(tarjeta), TarjetaDTO.class);
    }

    @Override
    public TarjetaDTO modificarTarjeta(TarjetaDTO tarjetaDTO) {
        return tarjetaRepositorio.findById(tarjetaDTO.getIdTarjeta())
                .map(existing -> {
                    Tarjeta tarjetaEntidad = modelMapper.map(tarjetaDTO, Tarjeta.class);
                    Tarjeta actualizada = tarjetaRepositorio.save(tarjetaEntidad);
                    return modelMapper.map(actualizada, TarjetaDTO.class);
                })
                .orElseThrow(() -> new CustomExceptions.PagoNotFoundException(
                        "Pago con ID " + tarjetaDTO.getIdTarjeta() + " no encontrado"));
    }

    @Override
    public void eliminarTarjeta(Long id) {
        if (!tarjetaRepositorio.existsById(id)) {
            throw new CustomExceptions.PagoNotFoundException("Pago no encontrado con ID: " + id);
        }
        tarjetaRepositorio.deleteById(id);
    }

    @Override
    public TarjetaDTO buscarTarjetaPorId(Long id) {
        return tarjetaRepositorio.findById(id)
                .map(tarjeta -> modelMapper.map(tarjeta, TarjetaDTO.class))
                .orElseThrow(() -> new CustomExceptions.PagoNotFoundException(
                        "Pago con ID " + id + " no encontrado"));
    }

    @Override
    public List<TarjetaDTO> listarTarjetas() {
        return tarjetaRepositorio.findAll().stream()
                .map(tarjeta -> modelMapper.map(tarjeta, TarjetaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TarjetaDTO> listarPorCliente(Long idCliente) {

        return tarjetaRepositorio.findByIdCliente(idCliente).stream()
                .map(tarjeta -> modelMapper.map(tarjeta, TarjetaDTO.class))
                .collect(Collectors.toList());
    }
}
