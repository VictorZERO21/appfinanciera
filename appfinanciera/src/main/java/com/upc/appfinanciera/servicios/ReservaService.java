package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.entidades.*;
import com.upc.appfinanciera.interfaces.IReservaService;
import com.upc.appfinanciera.repositorios.*;
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
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private TarjetaRepositorio tarjetaRepositorio;
    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Override
    public ReservaDTO insertarReserva(ReservaDTO reservaDTO) {
        Long idAsesor = reservaDTO.getAsesor().getIdAsesor();
        Long idCliente = reservaDTO.getCliente().getIdCliente();
        Long idPago = reservaDTO.getTarjeta().getIdTarjeta();
        LocalDate fechaReserva = reservaDTO.getFechaHoraInicio().toLocalDate();
        LocalTime horaInicioReserva = reservaDTO.getFechaHoraInicio().toLocalTime();
        LocalTime horaFinReserva = reservaDTO.getFechaHoraFin().toLocalTime();
        Disponibilidad disponibilidad = disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        idAsesor, fechaReserva, horaInicioReserva, horaFinReserva
                )
                .orElseThrow(() -> new RuntimeException("El asesor con ID " + idAsesor +
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
        AsesorFinanciero asesor = asesorRepositorio.findById(idAsesor)
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado"));
        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Tarjeta tarjeta = tarjetaRepositorio.findById(idPago)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        reserva.setAsesor(asesor);
        reserva.setCliente(cliente);
        reserva.setTarjeta(tarjeta);
        reserva.setMontoTotal(reservaDTO.getMontoTotal());
        Reserva guardado = reservaRepositorio.save(reserva);
        disponibilidad.setDisponible(false);
        disponibilidadRepositorio.save(disponibilidad);
        return modelMapper.map(guardado, ReservaDTO.class);
    }

    @Override
    public ReservaDTO modificarReserva(ReservaDTO reservaDTO) {
        Reserva existente = reservaRepositorio.findById(reservaDTO.getIdReserva())
                .orElseThrow(() -> new RuntimeException(
                        "Reserva con ID " + reservaDTO.getIdReserva() + " no encontrada"));
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

        if (existeChoque && !existente.getIdReserva().equals(reservaDTO.getIdReserva())) {
            throw new RuntimeException("El asesor ya tiene otra reserva en ese horario");
        }
        existente.setFechaHoraInicio(reservaDTO.getFechaHoraInicio());
        existente.setFechaHoraFin(reservaDTO.getFechaHoraFin());
        existente.setEstado(reservaDTO.getEstado());
        existente.setModalidad(reservaDTO.getModalidad());
        existente.setAsesor(asesorRepositorio.findById(idAsesor)
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado")));
        existente.setCliente(clienteRepositorio.findById(reservaDTO.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        existente.setTarjeta(tarjetaRepositorio.findById(reservaDTO.getTarjeta().getIdTarjeta())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado")));
        Reserva actualizado = reservaRepositorio.save(existente);
        return modelMapper.map(actualizado, ReservaDTO.class);
    }

    @Override
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

        Long idAsesor = reserva.getAsesor().getIdAsesor();
        LocalDate fechaReserva = reserva.getFechaHoraInicio().toLocalDate();
        LocalTime horaInicio = reserva.getFechaHoraInicio().toLocalTime();
        LocalTime horaFin = reserva.getFechaHoraFin().toLocalTime();

        Disponibilidad disponibilidad = disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        idAsesor, fechaReserva, horaInicio, horaFin
                )
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró la disponibilidad del asesor con ID " + idAsesor +
                                " en " + fechaReserva + " de " + horaInicio + " a " + horaFin));

        disponibilidad.setDisponible(true);
        disponibilidadRepositorio.save(disponibilidad);

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
