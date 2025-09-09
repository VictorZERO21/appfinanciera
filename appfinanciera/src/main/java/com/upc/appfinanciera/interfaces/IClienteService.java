package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.entidades.Cliente;
import java.util.List;

public interface IClienteService {
    public Cliente insertar(Cliente cliente);
    public Cliente actualizar(Cliente cliente);
    public void eliminar(String dni);
    public Cliente buscarPorDni(String dni);
    public List<Cliente> buscarTodos();
}
