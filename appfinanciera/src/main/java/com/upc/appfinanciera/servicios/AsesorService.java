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
    public AsesorFinancieroDTO insertar(AsesorFinancieroDTO asesorDto) {
        AsesorFinanciero asesorEntidad = modelMapper.map(asesorDto, AsesorFinanciero.class);
        AsesorFinanciero guardado = asesorRepositorio.save(asesorEntidad);
        return modelMapper.map(guardado, AsesorFinancieroDTO.class); // Convertir a DTO
    }

    @Override
    public AsesorFinancieroDTO actualizar(AsesorFinancieroDTO asesorDto) {
        AsesorFinanciero asesorEntidad = modelMapper.map(asesorDto, AsesorFinanciero.class);
        AsesorFinanciero guardado = asesorRepositorio.save(asesorEntidad);
        return modelMapper.map(guardado, AsesorFinancieroDTO.class); // Convertir a DTO
    }

    @Override
    public void eliminar(String dni) {
        AsesorFinanciero asesor = asesorRepositorio.findByDni(dni);
        if (asesor == null) {
            throw new AsesorNotFoundException("Asesor con DNI " + dni + " no encontrado");
        }
        asesorRepositorio.delete(asesor);
    }

    @Override
    public AsesorFinancieroDTO buscarPorDni(String dni) {
        AsesorFinanciero asesor = asesorRepositorio.findByDni(dni);
        if (asesor == null) {
            throw new AsesorNotFoundException("Asesor con DNI " + dni + " no encontrado");
        }
        return modelMapper.map(asesor, AsesorFinancieroDTO.class); // Convertir a DTO
    }

    @Override
    public List<AsesorFinancieroDTO> buscarTodos() {
        List<AsesorFinanciero> asesores = asesorRepositorio.findAll();
        return asesores.stream()
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class)) // Convertir a DTO
                .collect(Collectors.toList());
    }
}
