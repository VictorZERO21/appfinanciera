package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.entidades.*;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.interfaces.IReservaService;
import com.upc.appfinanciera.repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private DisponibilidadRepositorio disponibilidadRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private TarjetaRepositorio tarjetaRepositorio;

    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ChatRepositorio chatRepositorio;

    @Autowired
    private MensajeRepositorio mensajeRepositorio;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ReservaDTO insertarReserva(ReservaDTO reservaDTO) {
        Long idAsesor = reservaDTO.getAsesor().getIdAsesor();
        Long idCliente = reservaDTO.getCliente().getIdCliente();
        Long idPago = reservaDTO.getTarjeta().getIdTarjeta();

        LocalDate fechaReserva = reservaDTO.getFechaHoraInicio().toLocalDate();
        LocalTime horaInicio = reservaDTO.getFechaHoraInicio().toLocalTime();
        LocalTime horaFin = reservaDTO.getFechaHoraFin().toLocalTime();
        
        Disponibilidad disponibilidad = disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        idAsesor, fechaReserva, horaInicio, horaFin
                )
                .orElseThrow(() -> new CustomExceptions.DisponibilidadNotFoundException(
                        "El asesor con ID " + idAsesor + " no tiene disponibilidad registrada en " + fechaReserva +
                                " de " + horaInicio + " a " + horaFin));

        if (!disponibilidad.isDisponible()) {
            throw new CustomExceptions.ValidationException("El asesor está ocupado en ese horario");
        }
        
        boolean existeChoque = reservaRepositorio
                .existsByAsesor_IdAsesorAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
                        idAsesor,
                        reservaDTO.getFechaHoraFin(),
                        reservaDTO.getFechaHoraInicio()
                );
        if (existeChoque) {
            throw new CustomExceptions.ValidationException("El asesor ya tiene otra reserva en ese horario");
        }
        
        AsesorFinanciero asesor = asesorRepositorio.findById(idAsesor)
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException("Asesor no encontrado"));
        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException("Cliente no encontrado"));
        Tarjeta tarjeta = tarjetaRepositorio.findById(idPago)
                .orElseThrow(() -> new CustomExceptions.PagoNotFoundException("Pago no encontrado"));
        
        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        reserva.setAsesor(asesor);
        reserva.setCliente(cliente);
        reserva.setTarjeta(tarjeta);
        reserva.setMontoTotal(reservaDTO.getMontoTotal());

        Reserva guardado = reservaRepositorio.save(reserva);
        
        disponibilidad.setDisponible(false);
        disponibilidadRepositorio.save(disponibilidad);
        
        chatRepositorio.findByCliente_IdClienteAndAsesor_IdAsesor(idCliente, idAsesor)
                .stream()
                .findFirst()
                .or(() -> {
                    Chat nuevoChat = new Chat();
                    nuevoChat.setCliente(cliente);
                    nuevoChat.setAsesor(asesor);
                    chatRepositorio.save(nuevoChat);
                    
                    Mensaje mensajeInicial = new Mensaje();
                    mensajeInicial.setChat(nuevoChat);
                    mensajeInicial.setContenido("👋 Hola, esta conversación se creó con tu primera reserva con el asesor.");
                    mensajeInicial.setEmisor("SISTEMA");
                    mensajeRepositorio.save(mensajeInicial);

                    return java.util.Optional.of(nuevoChat);
                });
        
        return modelMapper.map(guardado, ReservaDTO.class);
    }

    @Override
    public ReservaDTO modificarReserva(ReservaDTO reservaDTO) {
        Reserva existente = reservaRepositorio.findById(reservaDTO.getIdReserva())
                .orElseThrow(() -> new CustomExceptions.ReservaNotFoundException(
                        "Reserva con ID " + reservaDTO.getIdReserva() + " no encontrada"));

        Long idAsesor = reservaDTO.getAsesor().getIdAsesor();
        LocalDate fechaReserva = reservaDTO.getFechaHoraInicio().toLocalDate();
        LocalTime horaInicio = reservaDTO.getFechaHoraInicio().toLocalTime();
        LocalTime horaFin = reservaDTO.getFechaHoraFin().toLocalTime();

        Disponibilidad disponibilidad = disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        idAsesor, fechaReserva, horaInicio, horaFin)
                .orElseThrow(() -> new CustomExceptions.DisponibilidadNotFoundException(
                        "El asesor con ID " + idAsesor + " no tiene disponibilidad registrada en "
                                + fechaReserva + " de " + horaInicio + " a " + horaFin));

        if (!disponibilidad.isDisponible()) {
            throw new CustomExceptions.ValidationException("El asesor no está disponible en ese horario");
        }

        boolean existeChoque = reservaRepositorio
                .existsByAsesor_IdAsesorAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
                        idAsesor,
                        reservaDTO.getFechaHoraFin(),
                        reservaDTO.getFechaHoraInicio());

        if (existeChoque && !existente.getIdReserva().equals(reservaDTO.getIdReserva())) {
            throw new CustomExceptions.ValidationException("El asesor ya tiene otra reserva en ese horario");
        }

        existente.setFechaHoraInicio(reservaDTO.getFechaHoraInicio());
        existente.setFechaHoraFin(reservaDTO.getFechaHoraFin());
        existente.setEstado(reservaDTO.getEstado());
        existente.setModalidad(reservaDTO.getModalidad());
        existente.setMontoTotal(reservaDTO.getMontoTotal());
        existente.setAsesor(asesorRepositorio.findById(idAsesor)
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException("Asesor no encontrado con ID: " + idAsesor)));
        existente.setCliente(clienteRepositorio.findById(reservaDTO.getCliente().getIdCliente())
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException("Cliente no encontrado con ID: " + reservaDTO.getCliente().getIdCliente())));
        existente.setTarjeta(tarjetaRepositorio.findById(reservaDTO.getTarjeta().getIdTarjeta())
                .orElseThrow(() -> new CustomExceptions.PagoNotFoundException("Pago no encontrado con ID: " + reservaDTO.getTarjeta().getIdTarjeta())));

        Reserva actualizado = reservaRepositorio.save(existente);
        return modelMapper.map(actualizado, ReservaDTO.class);
    }

    @Override
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepositorio.findById(id)
                .orElseThrow(() -> new CustomExceptions.ReservaNotFoundException("Reserva no encontrada con ID: " + id));

        Long idAsesor = reserva.getAsesor().getIdAsesor();
        LocalDate fecha = reserva.getFechaHoraInicio().toLocalDate();
        LocalTime horaInicio = reserva.getFechaHoraInicio().toLocalTime();
        LocalTime horaFin = reserva.getFechaHoraFin().toLocalTime();

        Disponibilidad disponibilidad = disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        idAsesor, fecha, horaInicio, horaFin)
                .orElseThrow(() -> new CustomExceptions.DisponibilidadNotFoundException(
                        "No se encontró la disponibilidad del asesor con ID " + idAsesor +
                                " en " + fecha + " de " + horaInicio + " a " + horaFin));

        disponibilidad.setDisponible(true);
        disponibilidadRepositorio.save(disponibilidad);

        reservaRepositorio.deleteById(id);
    }

    @Override
    public ReservaDTO buscarReservaPorId(Long id) {
        return reservaRepositorio.findById(id)
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .orElseThrow(() -> new CustomExceptions.ReservaNotFoundException("Reserva con ID " + id + " no encontrada"));
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
    public List<ReservaDTO> listarReservasPorAsesorId(Long idAsesor) {
        return reservaRepositorio.findByAsesor_IdAsesor(idAsesor).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<ClienteDTO> listarClientesConReservas() {
        return reservaRepositorio.findAll().stream()
                .map(Reserva::getCliente)
                .collect(Collectors.toMap(
                        Cliente::getIdCliente,
                        c -> c,
                        (c1, c2) -> c1
                ))
                .values()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}

