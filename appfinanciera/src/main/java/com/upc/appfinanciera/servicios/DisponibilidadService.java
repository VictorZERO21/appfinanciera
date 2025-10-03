package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.DisponibilidadDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Disponibilidad;
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
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado con id: " + disponibilidadDto.getIdAsesor()));

        LocalTime inicio = disponibilidadDto.getHoraInicio();
        LocalTime fin = disponibilidadDto.getHoraFin();

        List<DisponibilidadDTO> bloques = new ArrayList<>();

        while (inicio.isBefore(fin)) {
            LocalTime bloqueFin = inicio.plusHours(2);
            if (bloqueFin.isAfter(fin)) bloqueFin = fin; // último bloque si sobra menos de 2h

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
        return disponibilidadRepositorio.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisponibilidadDTO> buscarPorAsesor(Long idAsesor) {
        if (!asesorRepositorio.existsById(idAsesor)) {
            throw new RuntimeException("Asesor no encontrado con id: " + idAsesor);
        }
        return disponibilidadRepositorio.findByAsesorFinancieroIdAsesor(idAsesor)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long idDisponibilidad) {
        if (!disponibilidadRepositorio.existsById(idDisponibilidad)) {
            throw new RuntimeException("Disponibilidad no encontrada con id: " + idDisponibilidad);
        }
        disponibilidadRepositorio.deleteById(idDisponibilidad);
    }

    @Override
    public DisponibilidadDTO actualizar(DisponibilidadDTO disponibilidadDto) {
        if (disponibilidadDto.getIdDisponibilidad() == null) {
            throw new RuntimeException("El idDisponibilidad es requerido para actualizar.");
        }
        Long id = disponibilidadDto.getIdDisponibilidad();
        Disponibilidad existente = disponibilidadRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada con id: " + id));

        disponibilidadRepositorio
                .findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
                        disponibilidadDto.getIdAsesor(),
                        disponibilidadDto.getFecha(),
                        disponibilidadDto.getHoraInicio(),
                        disponibilidadDto.getHoraFin()
                )
                .ifPresent(d -> {
                    if (!d.getIdDisponibilidad().equals(id)) {
                        throw new RuntimeException("Ya existe otra disponibilidad en ese rango para este asesor");
                    }
                });
        existente.setFecha(disponibilidadDto.getFecha());
        existente.setHoraInicio(disponibilidadDto.getHoraInicio());
        existente.setHoraFin(disponibilidadDto.getHoraFin());
        existente.setDisponible(disponibilidadDto.getDisponible());

        AsesorFinanciero asesor = asesorRepositorio.findById(disponibilidadDto.getIdAsesor())
                .orElseThrow(() -> new RuntimeException(
                        "Asesor no encontrado con id: " + disponibilidadDto.getIdAsesor()));
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
