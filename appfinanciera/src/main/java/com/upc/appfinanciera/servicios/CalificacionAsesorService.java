package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.CalificacionAsesorDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.CalificacionAsesor;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.interfaces.ICalificacionAsesorService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.CalificacionAsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ModelMapper modelMapper;

    @Override
    public CalificacionAsesorDTO insertar(CalificacionAsesorDTO dto) {
        AsesorFinanciero asesor = asesorRepositorio.findById(dto.getIdAsesor())
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado"));
        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        CalificacionAsesor calificacion = new CalificacionAsesor();
        calificacion.setAsesor(asesor);
        calificacion.setCliente(cliente);
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());

        CalificacionAsesor saved = calificacionRepositorio.save(calificacion);
        return modelMapper.map(saved, CalificacionAsesorDTO.class);
    }

    @Override
    public List<CalificacionAsesorDTO> listarPorAsesor(Long idAsesor) {
        return calificacionRepositorio.findByAsesor_IdAsesor(idAsesor).stream()
                .map(calificacion -> modelMapper.map(calificacion, CalificacionAsesorDTO.class))
                .collect(Collectors.toList());
    }
}
