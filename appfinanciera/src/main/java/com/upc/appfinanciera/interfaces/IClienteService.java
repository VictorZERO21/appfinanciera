package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ClienteDTO;
import java.util.List;

public interface IClienteService {
    ClienteDTO buscarClientePorId(Long id);
    List<ClienteDTO> listarClientes();
}


