package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.excepciones.CustomExceptions.AsesorNotFoundException;
import com.upc.appfinanciera.interfaces.IAsesorService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsesorService implements IAsesorService {
    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AsesorFinancieroDTO insertarAsesor(AsesorFinancieroDTO asesorDTO) {
        AsesorFinanciero asesor = modelMapper.map(asesorDTO, AsesorFinanciero.class);
        return modelMapper.map(asesorRepositorio.save(asesor), AsesorFinancieroDTO.class);
    }

    @Override
    public AsesorFinancieroDTO modificarAsesor(AsesorFinancieroDTO asesorDTO) {
        return asesorRepositorio.findById(asesorDTO.getIdAsesor())
                .map(existing -> {
                    AsesorFinanciero asesorEntidad = modelMapper.map(asesorDTO, AsesorFinanciero.class);
                    return modelMapper.map(asesorRepositorio.save(asesorEntidad), AsesorFinancieroDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Asesor con ID " + asesorDTO.getIdAsesor() + " no encontrado"));
    }

    @Override
    public void eliminarAsesor(Long id) {
        if (!asesorRepositorio.existsById(id)) {
            throw new RuntimeException("Asesor no encontrado con ID: " + id);
        }
        asesorRepositorio.deleteById(id);
    }

    @Override
    public AsesorFinancieroDTO buscarAsesorPorId(Long id) {
        return asesorRepositorio.findById(id)
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
                .orElseThrow(() -> new RuntimeException("Asesor con ID " + id + " no encontrado"));
    }

    @Override
    public AsesorFinancieroDTO buscarAsesorPorDni(String dni) {
        AsesorFinanciero asesor = asesorRepositorio.findByDni(dni);
        if (asesor == null) {
            throw new RuntimeException("Asesor con DNI " + dni + " no encontrado");
        }
        return modelMapper.map(asesor, AsesorFinancieroDTO.class);
    }

    @Override
    public AsesorFinancieroDTO buscarAsesorPorEmail(String email) {
        AsesorFinanciero asesor = asesorRepositorio.findByEmail(email);
        if (asesor == null) {
            throw new RuntimeException("Asesor con email " + email + " no encontrado");
        }
        return modelMapper.map(asesor, AsesorFinancieroDTO.class);
    }

    @Override
    public List<AsesorFinancieroDTO> listarAsesores() {
        return asesorRepositorio.findAll().stream()
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AsesorFinancieroDTO> buscarAsesoresPorNombre(String nombre) {
        return asesorRepositorio.findByNombreContainingIgnoreCase(nombre).stream()
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
                .collect(Collectors.toList());
    }

 // @Override
 // public List<AsesorFinancieroDTO> buscarAsesoresPorEspecialidad(String especialidad) {
 //     return asesorRepositorio.findByEspecialidadContainingIgnoreCase(especialidad).stream()
 //             .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
 //             .collect(Collectors.toList());
 // }
}
