package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ConsejoDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Consejo;
import com.upc.appfinanciera.excepciones.CustomExceptions.ConsejoNotFoundException;
import com.upc.appfinanciera.interfaces.IConsejoService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.ConsejoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsejoService implements IConsejoService {

    @Autowired
    private ConsejoRepositorio consejoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ConsejoDTO insertar(ConsejoDTO consejoDTO) {
        // Buscar Cliente por DNI
        Cliente cliente = clienteRepositorio.findByDni(consejoDTO.getDniCliente());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con DNI: " + consejoDTO.getDniCliente());
        }

        // Buscar AsesorFinanciero por DNI
        AsesorFinanciero asesor = asesorRepositorio.findByDni(consejoDTO.getDniAsesor());
        if (asesor == null) {
            throw new RuntimeException("Asesor no encontrado con DNI: " + consejoDTO.getDniAsesor());
        }

        // Crear nueva entidad Consejo
        Consejo consejo = new Consejo();
        consejo.setTitulo(consejoDTO.getTitulo());
        consejo.setContenido(consejoDTO.getContenido());
        consejo.setCliente(cliente);  // Asociar Cliente
        consejo.setAsesorFinanciero(asesor);  // Asociar Asesor

        // Guardar el consejo en la base de datos
        Consejo savedConsejo = consejoRepositorio.save(consejo);

        // Convertir a DTO y devolver
        return modelMapper.map(savedConsejo, ConsejoDTO.class);
    }

    @Override
    public ConsejoDTO actualizar(ConsejoDTO consejoDto) {
        return consejoRepositorio.findById(consejoDto.getIdConsejo())
                .map(existing -> {
                    Consejo consejoEntidad = modelMapper.map(consejoDto, Consejo.class);
                    Consejo guardado = consejoRepositorio.save(consejoEntidad);
                    return modelMapper.map(guardado, ConsejoDTO.class);
                })
                .orElseThrow(() -> new ConsejoNotFoundException("Consejo con ID " + consejoDto.getIdConsejo() + " no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        if (!consejoRepositorio.existsById(id)) {
            throw new ConsejoNotFoundException("Consejo no encontrado con ID: " + id);
        }
        consejoRepositorio.deleteById(id);
    }

    @Override
    public List<ConsejoDTO> buscarPorCliente(String dniCliente) {

        List<Consejo> consejos = consejoRepositorio.findByClienteDni(dniCliente);
        return consejos.stream()
                .map(consejo -> modelMapper.map(consejo, ConsejoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsejoDTO> buscarTodos() {
        List<Consejo> consejos = consejoRepositorio.findAll();
        return consejos.stream()
                .map(consejo -> modelMapper.map(consejo, ConsejoDTO.class))
                .collect(Collectors.toList());
    }
}
