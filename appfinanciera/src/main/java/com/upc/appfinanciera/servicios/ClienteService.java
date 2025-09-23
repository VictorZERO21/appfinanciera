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
    public ClienteDTO insertarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return modelMapper.map(clienteRepositorio.save(cliente), ClienteDTO.class);
    }

    @Override
    public ClienteDTO modificarCliente(ClienteDTO clienteDTO) {
        return clienteRepositorio.findById(clienteDTO.getIdCliente())
                .map(existing -> {
                    Cliente clienteEntidad = modelMapper.map(clienteDTO, Cliente.class);
                    return modelMapper.map(clienteRepositorio.save(clienteEntidad), ClienteDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteDTO.getIdCliente() + " no encontrado"));
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        clienteRepositorio.deleteById(id);
    }

    @Override
    public ClienteDTO buscarClientePorId(Long id) {
        return clienteRepositorio.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + id + " no encontrado"));
    }

    @Override
    public ClienteDTO buscarClientePorDni(String dni) {
        Cliente cliente = clienteRepositorio.findByDni(dni);
        if (cliente == null) {
            throw new RuntimeException("Cliente con DNI " + dni + " no encontrado");
        }
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO buscarClientePorEmail(String email) {
        Cliente cliente = clienteRepositorio.findByEmail(email);
        if (cliente == null) {
            throw new RuntimeException("Cliente con email " + email + " no encontrado");
        }
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepositorio.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> buscarClientesPorNombre(String nombre) {
        return clienteRepositorio.findByNombreContainingIgnoreCase(nombre).stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}
