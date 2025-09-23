package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.dto.GestionFinancieraDTO;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.GestionFinanciera;
import com.upc.appfinanciera.excepciones.CustomExceptions.GestionFinancieraNotFoundException;
import com.upc.appfinanciera.interfaces.IGestionFinancieraService;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.GestionFinancieraRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionFinancieraService implements IGestionFinancieraService {
    @Autowired
    private GestionFinancieraRepositorio gestionFinancieraRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GestionFinancieraDTO insertar(GestionFinancieraDTO gestionFinancieraDTO) {
        // Obtener el Cliente completo desde el DTO (ya que es un objeto completo)
        Cliente cliente = modelMapper.map(gestionFinancieraDTO.getCliente(), Cliente.class);  // Mapea de ClienteDTO a Cliente

        // Crear la nueva entidad GestionFinanciera
        GestionFinanciera gestionFinanciera = new GestionFinanciera();
        gestionFinanciera.setTitulo(gestionFinancieraDTO.getTitulo());
        gestionFinanciera.setTipo(gestionFinancieraDTO.getTipo());
        gestionFinanciera.setMonto(gestionFinancieraDTO.getMonto());
        gestionFinanciera.setFecha(gestionFinancieraDTO.getFecha());
        gestionFinanciera.setCliente(cliente);  // Asociar el Cliente completo

        // Guardar la gestión financiera en la base de datos
        GestionFinanciera savedGestionFinanciera = gestionFinancieraRepositorio.save(gestionFinanciera);

        // Convertir la entidad guardada en un DTO y devolver
        return modelMapper.map(savedGestionFinanciera, GestionFinancieraDTO.class);
    }

    @Override
    public GestionFinancieraDTO actualizar(GestionFinancieraDTO gestionFinancieraDto) {
        return gestionFinancieraRepositorio.findById(gestionFinancieraDto.getIdGestion())
                .map(existing -> {
                    GestionFinanciera gestionEntidad = modelMapper.map(gestionFinancieraDto, GestionFinanciera.class);
                    GestionFinanciera guardado = gestionFinancieraRepositorio.save(gestionEntidad);
                    return modelMapper.map(guardado, GestionFinancieraDTO.class); // Convertir a DTO
                })
                .orElseThrow(() -> new GestionFinancieraNotFoundException("Gestión con ID " + gestionFinancieraDto.getIdGestion() + " no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        if (!gestionFinancieraRepositorio.existsById(id)) {
            throw new GestionFinancieraNotFoundException("Gestión no encontrada con ID: " + id);
        }
        gestionFinancieraRepositorio.deleteById(id);
    }

    @Override
    public List<GestionFinancieraDTO> buscarPorCliente(String dniCliente) {
        List<GestionFinanciera> gestiones = gestionFinancieraRepositorio.findByClienteDni(dniCliente);
        return gestiones.stream()
                .map(gestion -> modelMapper.map(gestion, GestionFinancieraDTO.class)) // Convertir a DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> reportePorTipo(String tipo) {
        return gestionFinancieraRepositorio.reportePorTipo(tipo); // Utilizando la consulta personalizada
    }

    @Override
    public List<Object[]> reportePorFecha(LocalDate fecha) {
        return gestionFinancieraRepositorio.reportePorFecha(fecha); // Utilizando la consulta personalizada
    }

    @Override
    public List<GestionFinancieraDTO> buscarTodos() {
        List<GestionFinanciera> gestiones = gestionFinancieraRepositorio.findAll();

        return gestiones.stream()
                .map(gestion -> {
                    GestionFinancieraDTO dto = modelMapper.map(gestion, GestionFinancieraDTO.class);
                    // Mapear Cliente a ClienteDTO correctamente
                    ClienteDTO clienteDTO = modelMapper.map(gestion.getCliente(), ClienteDTO.class);
                    dto.setCliente(clienteDTO);  // Asignar el DTO de Cliente
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
