package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Disponibilidad;
import com.upc.appfinanciera.entidades.Reserva;
import com.upc.appfinanciera.excepciones.CustomExceptions.ReservaNotFoundException;
import com.upc.appfinanciera.interfaces.IReservaService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.DisponibilidadRepositorio;
import com.upc.appfinanciera.repositorios.ReservaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DisponibilidadRepositorio disponibilidadRepositorio;

    @Override
    public ReservaDTO insertarReserva(ReservaDTO reservaDTO) {
        Long idAsesor = reservaDTO.getAsesor().getIdAsesor();
        LocalDate fechaReserva = reservaDTO.getFechaHoraInicio().toLocalDate();
        LocalTime horaInicioReserva = reservaDTO.getFechaHoraInicio().toLocalTime();
        LocalTime horaFinReserva = reservaDTO.getFechaHoraFin().toLocalTime();

        Disponibilidad disponibilidad = disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        idAsesor,
                        fechaReserva,
                        horaInicioReserva,
                        horaFinReserva
                )
                .orElseThrow(() -> new RuntimeException(
                        "El asesor con ID " + idAsesor +
                                " no tiene disponibilidad registrada en " + fechaReserva +
                                " de " + horaInicioReserva + " a " + horaFinReserva));

        if (!disponibilidad.isDisponible()) {
            throw new RuntimeException("El asesor no está disponible en ese horario");
        }

        boolean existeChoque = reservaRepositorio
                .existsByAsesor_IdAsesorAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
                        idAsesor,
                        reservaDTO.getFechaHoraFin(),
                        reservaDTO.getFechaHoraInicio()
                );

        if (existeChoque) {
            throw new RuntimeException("El asesor ya tiene otra reserva en ese horario");
        }

        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        Reserva guardado = reservaRepositorio.save(reserva);

        return modelMapper.map(guardado, ReservaDTO.class);
    }

    @Override
    public ReservaDTO modificarReserva(ReservaDTO reservaDTO) {
        return reservaRepositorio.findById(reservaDTO.getIdReserva())
                .map(existing -> {
                    Long idAsesor = reservaDTO.getAsesor().getIdAsesor();
                    LocalDate fechaReserva = reservaDTO.getFechaHoraInicio().toLocalDate();
                    LocalTime horaInicioReserva = reservaDTO.getFechaHoraInicio().toLocalTime();
                    LocalTime horaFinReserva = reservaDTO.getFechaHoraFin().toLocalTime();
                    Disponibilidad disponibilidad = disponibilidadRepositorio
                            .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                                    idAsesor,
                                    fechaReserva,
                                    horaInicioReserva,
                                    horaFinReserva
                            )
                            .orElseThrow(() -> new RuntimeException(
                                    "El asesor con ID " + idAsesor +
                                            " no tiene disponibilidad registrada en " + fechaReserva +
                                            " de " + horaInicioReserva + " a " + horaFinReserva));

                    if (!disponibilidad.isDisponible()) {
                        throw new RuntimeException("El asesor no está disponible en ese horario");
                    }

                    boolean existeChoque = reservaRepositorio
                            .existsByAsesor_IdAsesorAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
                                    idAsesor,
                                    reservaDTO.getFechaHoraFin(),
                                    reservaDTO.getFechaHoraInicio()
                            );

                    if (existeChoque && !existing.getIdReserva().equals(reservaDTO.getIdReserva())) {
                        throw new RuntimeException("El asesor ya tiene otra reserva en ese horario");
                    }

                    Reserva reservaEntidad = modelMapper.map(reservaDTO, Reserva.class);
                    Reserva actualizado = reservaRepositorio.save(reservaEntidad);

                    return modelMapper.map(actualizado, ReservaDTO.class);
                })
                .orElseThrow(() -> new RuntimeException(
                        "Reserva con ID " + reservaDTO.getIdReserva() + " no encontrada"));
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
