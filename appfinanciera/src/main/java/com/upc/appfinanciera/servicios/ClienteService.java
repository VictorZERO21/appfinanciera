package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.excepciones.CustomExceptions;
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
    public ClienteDTO buscarClientePorId(Long id) {
        return clienteRepositorio.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException(
                        "Cliente con ID " + id + " no encontrado"));
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        if (clientes.isEmpty()) {
            throw new CustomExceptions.ClienteNotFoundException("No existen clientes registrados en el sistema.");
        }

        return clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}
