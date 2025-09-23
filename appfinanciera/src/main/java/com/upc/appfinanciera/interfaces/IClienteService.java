package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ClienteDTO;
import java.util.List;

public interface IClienteService {
    ClienteDTO insertarCliente(ClienteDTO clienteDTO);
    ClienteDTO modificarCliente(ClienteDTO clienteDTO);
    void eliminarCliente(Long id);
    ClienteDTO buscarClientePorId(Long id);
    ClienteDTO buscarClientePorDni(String dni);
    ClienteDTO buscarClientePorEmail(String email);
    List<ClienteDTO> listarClientes();
    List<ClienteDTO> buscarClientesPorNombre(String nombre);
}
