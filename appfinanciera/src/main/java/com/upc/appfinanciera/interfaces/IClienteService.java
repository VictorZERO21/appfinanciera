package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ClienteDTO;
import java.util.List;

public interface IClienteService {
    public ClienteDTO insertar(ClienteDTO cliente);
    public ClienteDTO actualizar(ClienteDTO cliente);
    public void eliminar(String dni);
    public ClienteDTO buscarPorDni(String dni);
    public List<ClienteDTO> buscarTodos();
}
