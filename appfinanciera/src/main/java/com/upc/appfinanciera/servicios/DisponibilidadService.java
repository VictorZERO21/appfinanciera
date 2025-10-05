package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.DisponibilidadDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Disponibilidad;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.interfaces.IDisponibilidadService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.DisponibilidadRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibilidadService implements IDisponibilidadService {
    @Autowired
    private DisponibilidadRepositorio disponibilidadRepositorio;

    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DisponibilidadDTO> insertar(DisponibilidadDTO disponibilidadDto) {
        AsesorFinanciero asesor = asesorRepositorio.findById(disponibilidadDto.getIdAsesor())
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException(
                        "Asesor no encontrado con ID: " + disponibilidadDto.getIdAsesor()));

        LocalTime inicio = disponibilidadDto.getHoraInicio();
        LocalTime fin = disponibilidadDto.getHoraFin();

        if (inicio == null || fin == null || !inicio.isBefore(fin)) {
            throw new CustomExceptions.ValidationException("El rango de horas no es válido.");
        }

        List<DisponibilidadDTO> bloques = new ArrayList<>();

        while (inicio.isBefore(fin)) {
            LocalTime bloqueFin = inicio.plusHours(2);
            if (bloqueFin.isAfter(fin)) bloqueFin = fin;

            Disponibilidad disponibilidad = new Disponibilidad();
            disponibilidad.setFecha(disponibilidadDto.getFecha());
            disponibilidad.setHoraInicio(inicio);
            disponibilidad.setHoraFin(bloqueFin);
            disponibilidad.setDisponible(true);
            disponibilidad.setAsesorFinanciero(asesor);

            Disponibilidad guardado = disponibilidadRepositorio.save(disponibilidad);
            bloques.add(entityToDto(guardado));

            inicio = bloqueFin;
        }

        return bloques;
    }

    @Override
    public List<DisponibilidadDTO> buscarTodos() {
        List<Disponibilidad> lista = disponibilidadRepositorio.findAll();
        if (lista.isEmpty()) {
            throw new CustomExceptions.DisponibilidadNotFoundException("No hay disponibilidades registradas.");
        }
        return lista.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisponibilidadDTO> buscarPorAsesor(Long idAsesor) {
        if (!asesorRepositorio.existsById(idAsesor)) {
            throw new CustomExceptions.AsesorNotFoundException("Asesor no encontrado con ID: " + idAsesor);
        }

        List<Disponibilidad> lista = disponibilidadRepositorio.findByAsesorFinancieroIdAsesor(idAsesor);
        if (lista.isEmpty()) {
            throw new CustomExceptions.DisponibilidadNotFoundException(
                    "El asesor con ID " + idAsesor + " no tiene disponibilidades registradas.");
        }

        return lista.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long idDisponibilidad) {
        if (!disponibilidadRepositorio.existsById(idDisponibilidad)) {
            throw new CustomExceptions.DisponibilidadNotFoundException(
                    "Disponibilidad no encontrada con ID: " + idDisponibilidad);
        }
        disponibilidadRepositorio.deleteById(idDisponibilidad);
    }

    @Override
    public DisponibilidadDTO actualizar(DisponibilidadDTO disponibilidadDto) {
        if (disponibilidadDto.getIdDisponibilidad() == null) {
            throw new CustomExceptions.ValidationException("El ID de la disponibilidad es obligatorio para actualizar.");
        }

        Long id = disponibilidadDto.getIdDisponibilidad();

        Disponibilidad existente = disponibilidadRepositorio.findById(id)
                .orElseThrow(() -> new CustomExceptions.DisponibilidadNotFoundException(
                        "Disponibilidad no encontrada con ID: " + id));

        // Verificar duplicidad de rango horario
        disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        disponibilidadDto.getIdAsesor(),
                        disponibilidadDto.getFecha(),
                        disponibilidadDto.getHoraInicio(),
                        disponibilidadDto.getHoraFin()
                )
                .ifPresent(d -> {
                    if (!d.getIdDisponibilidad().equals(id)) {
                        throw new CustomExceptions.ValidationException(
                                "Ya existe otra disponibilidad en ese rango horario para este asesor.");
                    }
                });

        existente.setFecha(disponibilidadDto.getFecha());
        existente.setHoraInicio(disponibilidadDto.getHoraInicio());
        existente.setHoraFin(disponibilidadDto.getHoraFin());
        existente.setDisponible(disponibilidadDto.getDisponible());

        AsesorFinanciero asesor = asesorRepositorio.findById(disponibilidadDto.getIdAsesor())
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException(
                        "Asesor no encontrado con ID: " + disponibilidadDto.getIdAsesor()));
        existente.setAsesorFinanciero(asesor);

        Disponibilidad guardado = disponibilidadRepositorio.save(existente);
        return entityToDto(guardado);
    }

    private DisponibilidadDTO entityToDto(Disponibilidad disponibilidad) {
        DisponibilidadDTO dto = modelMapper.map(disponibilidad, DisponibilidadDTO.class);
        if (disponibilidad.getAsesorFinanciero() != null) {
            dto.setIdAsesor(disponibilidad.getAsesorFinanciero().getIdAsesor());
        }
        return dto;
    }
}
