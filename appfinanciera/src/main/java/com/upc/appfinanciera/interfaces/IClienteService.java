package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ClienteDTO;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    ClienteDTO buscarClientePorId(Long id);
    List<ClienteDTO> listarClientes();

    ClienteDTO findByEmail(String email);
}


