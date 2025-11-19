package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.excepciones.CustomExceptions.AsesorNotFoundException;
import com.upc.appfinanciera.interfaces.IAsesorService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsesorService implements IAsesorService {
    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AsesorFinancieroDTO buscarAsesorPorId(Long id) {
        return asesorRepositorio.findById(id)
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException(
                        "Asesor con ID " + id + " no encontrado"));
    }

    @Override
    public List<AsesorFinancieroDTO> listarAsesores() {
        List<AsesorFinanciero> asesores = asesorRepositorio.findAll();

        if (asesores.isEmpty()) {
            throw new CustomExceptions.AsesorNotFoundException("No existen asesores registrados en el sistema.");
        }

        return asesores.stream()
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AsesorFinancieroDTO findByEmail(String email) {
        return asesorRepositorio.findByEmail(email)
                .map(asesor -> modelMapper.map(asesor, AsesorFinancieroDTO.class))
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException(
                        "Asesor con email " + email + " no encontrado"));
    }
}
