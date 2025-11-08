package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.CalificacionAsesorDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.CalificacionAsesor;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Reserva;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.interfaces.ICalificacionAsesorService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.CalificacionAsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.ReservaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionAsesorService implements ICalificacionAsesorService {
    @Autowired
    private CalificacionAsesorRepositorio calificacionRepositorio;

    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CalificacionAsesorDTO insertar(CalificacionAsesorDTO dto) {
        Reserva ultimaReserva = reservaRepositorio
                .findTopByCliente_IdClienteAndAsesor_IdAsesorOrderByFechaHoraFinDesc(dto.getIdCliente(), dto.getIdAsesor())
                .orElseThrow(() -> new CustomExceptions.ReservaNotFoundException(
                        "No tienes reservas registradas con este asesor."));

        if (ultimaReserva.getFechaHoraFin().isAfter(LocalDateTime.now())) {
            throw new CustomExceptions.ValidationException(
                    "Solo puedes calificar después de que la reserva haya terminado.");
        }

        AsesorFinanciero asesor = asesorRepositorio.findById(dto.getIdAsesor())
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException(
                        "Asesor no encontrado con ID: " + dto.getIdAsesor()));

        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException(
                        "Cliente no encontrado con ID: " + dto.getIdCliente()));

        CalificacionAsesor calificacion = new CalificacionAsesor();
        calificacion.setAsesor(asesor);
        calificacion.setCliente(cliente);
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());

        CalificacionAsesor guardado = calificacionRepositorio.save(calificacion);
        return modelMapper.map(guardado, CalificacionAsesorDTO.class);
    }

    @Override
    public List<CalificacionAsesorDTO> listarPorAsesor(Long idAsesor) {
        if (!asesorRepositorio.existsById(idAsesor)) {
            throw new CustomExceptions.AsesorNotFoundException("Asesor no encontrado con ID: " + idAsesor);
        }

        List<CalificacionAsesor> calificaciones = calificacionRepositorio.findByAsesor_IdAsesor(idAsesor);
        if (calificaciones.isEmpty()) {
            throw new CustomExceptions.ValidationException(
                    "El asesor con ID " + idAsesor + " aún no tiene calificaciones registradas.");
        }

        return calificaciones.stream()
                .map(calificacion -> modelMapper.map(calificacion, CalificacionAsesorDTO.class))
                .collect(Collectors.toList());
    }
}

