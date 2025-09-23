package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.PagoDTO;
import com.upc.appfinanciera.entidades.Pago;
import com.upc.appfinanciera.interfaces.IPagoService;
import com.upc.appfinanciera.repositorios.PagoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService implements IPagoService {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PagoDTO insertarPago(PagoDTO pagoDTO) {
        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        return modelMapper.map(pagoRepositorio.save(pago), PagoDTO.class);
    }

    @Override
    public PagoDTO modificarPago(PagoDTO pagoDTO) {
        return pagoRepositorio.findById(pagoDTO.getIdPago())
                .map(existing -> {
                    Pago pagoEntidad = modelMapper.map(pagoDTO, Pago.class);
                    return modelMapper.map(pagoRepositorio.save(pagoEntidad), PagoDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Pago con ID " + pagoDTO.getIdPago() + " no encontrado"));
    }

    @Override
    public void eliminarPago(Long id) {
        if (!pagoRepositorio.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }
        pagoRepositorio.deleteById(id);
    }

    @Override
    public PagoDTO buscarPagoPorId(Long id) {
        return pagoRepositorio.findById(id)
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .orElseThrow(() -> new RuntimeException("Pago con ID " + id + " no encontrado"));
    }

    @Override
    public List<PagoDTO> listarPagos() {
        return pagoRepositorio.findAll().stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .collect(Collectors.toList());
    }
}
