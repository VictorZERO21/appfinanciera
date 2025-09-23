package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Reserva;
import com.upc.appfinanciera.excepciones.CustomExceptions.ReservaNotFoundException;
import com.upc.appfinanciera.interfaces.IReservaService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.ReservaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReservaDTO insertarReserva(ReservaDTO reservaDTO) {
        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        return modelMapper.map(reservaRepositorio.save(reserva), ReservaDTO.class);
    }

    @Override
    public ReservaDTO modificarReserva(ReservaDTO reservaDTO) {
        return reservaRepositorio.findById(reservaDTO.getIdReserva())
                .map(existing -> {
                    Reserva reservaEntidad = modelMapper.map(reservaDTO, Reserva.class);
                    return modelMapper.map(reservaRepositorio.save(reservaEntidad), ReservaDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Reserva con ID " + reservaDTO.getIdReserva() + " no encontrada"));
    }

    @Override
    public void eliminarReserva(Long id) {
        if (!reservaRepositorio.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
        reservaRepositorio.deleteById(id);
    }

    @Override
    public ReservaDTO buscarReservaPorId(Long id) {
        return reservaRepositorio.findById(id)
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .orElseThrow(() -> new RuntimeException("Reserva con ID " + id + " no encontrada"));
    }

    @Override
    public List<ReservaDTO> listarReservas() {
        return reservaRepositorio.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> listarReservasPorClienteId(Long idCliente) {
        return reservaRepositorio.findByCliente_IdCliente(idCliente).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> listarReservasPorClienteDni(String dniCliente) {
        return reservaRepositorio.findByCliente_Dni(dniCliente).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> listarReservasPorAsesorId(Long idAsesor) {
        return reservaRepositorio.findByAsesor_IdAsesor(idAsesor).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> listarReservasPorAsesorDni(String dniAsesor) {
        return reservaRepositorio.findByAsesor_Dni(dniAsesor).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }
}



