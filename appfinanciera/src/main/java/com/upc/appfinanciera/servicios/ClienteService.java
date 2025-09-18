package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.excepciones.CustomExceptions.ClienteNotFoundException;
import com.upc.appfinanciera.interfaces.IClienteService;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements IClienteService {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClienteDTO insertar(ClienteDTO clienteDto) {
        Cliente clienteEntidad = modelMapper.map(clienteDto, Cliente.class);
        Cliente guardado = clienteRepositorio.save(clienteEntidad);
        return modelMapper.map(guardado, ClienteDTO.class); // Convertir a DTO
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDto) {
        Cliente clienteEntidad = modelMapper.map(clienteDto, Cliente.class);
        Cliente guardado = clienteRepositorio.save(clienteEntidad);
        return modelMapper.map(guardado, ClienteDTO.class); // Convertir a DTO
    }

    @Override
    public void eliminar(String dni) {
        Cliente cliente = clienteRepositorio.findByDni(dni);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente con DNI " + dni + " no encontrado");
        }
        clienteRepositorio.delete(cliente);
    }

    @Override
    public ClienteDTO buscarPorDni(String dni) {
        Cliente cliente = clienteRepositorio.findByDni(dni);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente con DNI " + dni + " no encontrado");
        }
        return modelMapper.map(cliente, ClienteDTO.class); // Convertir a DTO
    }

    @Override
    public List<ClienteDTO> buscarTodos() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class)) // Convertir a DTO
                .collect(Collectors.toList());
    }
}
