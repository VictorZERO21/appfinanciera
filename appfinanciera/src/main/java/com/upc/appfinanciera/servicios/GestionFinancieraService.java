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
        Cliente cliente = clienteRepositorio.findById(gestionFinancieraDTO.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: "
                        + gestionFinancieraDTO.getCliente().getIdCliente()));

        GestionFinanciera gestionFinanciera = new GestionFinanciera();
        gestionFinanciera.setTitulo(gestionFinancieraDTO.getTitulo());
        gestionFinanciera.setTipo(gestionFinancieraDTO.getTipo());
        gestionFinanciera.setMonto(gestionFinancieraDTO.getMonto());
        gestionFinanciera.setFecha(gestionFinancieraDTO.getFecha());
        gestionFinanciera.setCliente(cliente);
        GestionFinanciera savedGestionFinanciera = gestionFinancieraRepositorio.save(gestionFinanciera);
        return modelMapper.map(savedGestionFinanciera, GestionFinancieraDTO.class);
    }

    public GestionFinancieraDTO actualizar(GestionFinancieraDTO gestionFinancieraDto) {
        GestionFinanciera existente = gestionFinancieraRepositorio.findById(gestionFinancieraDto.getIdGestion())
                .orElseThrow(() -> new GestionFinancieraNotFoundException(
                        "Gestión con ID " + gestionFinancieraDto.getIdGestion() + " no encontrada"));
        Cliente cliente = clienteRepositorio.findById(gestionFinancieraDto.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: "
                        + gestionFinancieraDto.getCliente().getIdCliente()));
        existente.setTitulo(gestionFinancieraDto.getTitulo());
        existente.setTipo(gestionFinancieraDto.getTipo());
        existente.setMonto(gestionFinancieraDto.getMonto());
        existente.setFecha(gestionFinancieraDto.getFecha());
        existente.setCliente(cliente);
        GestionFinanciera actualizado = gestionFinancieraRepositorio.save(existente);
        GestionFinancieraDTO dto = modelMapper.map(actualizado, GestionFinancieraDTO.class);
        dto.setCliente(modelMapper.map(cliente, ClienteDTO.class));
        return dto;
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
