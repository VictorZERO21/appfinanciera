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
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsesorRepositorio asesorFinancieroRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReservaDTO insertar(ReservaDTO reservaDTO) {
        // Buscar Cliente por DNI
        Cliente cliente = clienteRepositorio.findByDni(reservaDTO.getDniCliente());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con DNI: " + reservaDTO.getDniCliente());
        }

        // Buscar AsesorFinanciero por DNI
        AsesorFinanciero asesor = asesorFinancieroRepositorio.findByDni(reservaDTO.getDniAsesor());
        if (asesor == null) {
            throw new RuntimeException("Asesor no encontrado con DNI: " + reservaDTO.getDniAsesor());
        }

        // Crear nueva Reserva y asociar Cliente y AsesorFinanciero
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);  // Asociar Cliente
        reserva.setAsesorFinanciero(asesor);  // Asociar Asesor
        reserva.setFechaHoraInicio(reservaDTO.getFechaHoraInicio());
        reserva.setFechaHoraFin(reservaDTO.getFechaHoraFin());
        reserva.setEstado(reservaDTO.getEstado());
        reserva.setModalidad(reservaDTO.getModalidad());

        // Guardar la reserva en la base de datos
        Reserva savedReserva = reservaRepositorio.save(reserva);

        // Convertir a DTO y devolver
        return modelMapper.map(savedReserva, ReservaDTO.class);
    }

    @Override
    public ReservaDTO actualizar(ReservaDTO reservaDto) {
        return reservaRepositorio.findById(reservaDto.getIdReserva())
                .map(existing -> {
                    Reserva reservaEntidad = modelMapper.map(reservaDto, Reserva.class);
                    Reserva guardado = reservaRepositorio.save(reservaEntidad);
                    return modelMapper.map(guardado, ReservaDTO.class); // Convertir a DTO
                })
                .orElseThrow(() -> new ReservaNotFoundException("Reserva con ID " + reservaDto.getIdReserva() + " no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        if (!reservaRepositorio.existsById(id)) {
            throw new ReservaNotFoundException("Reserva no encontrada con ID: " + id);
        }
        reservaRepositorio.deleteById(id);
    }

    @Override
    public List<ReservaDTO> buscarPorCliente(String dniCliente) {
        List<Reserva> reservas = reservaRepositorio.findByClienteDni(dniCliente); // Usar el nombre correcto
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class)) // Convertir a DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> buscarTodos() {
        List<Reserva> reservas = reservaRepositorio.findAll();
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class)) // Convertir a DTO
                .collect(Collectors.toList());
    }
}

