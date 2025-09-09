package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public Cliente insertar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminar(String dni) {
        clienteRepositorio.deleteById(clienteRepositorio.findByDni(dni).getIdCliente());
    }

    @Override
    public Cliente buscarPorDni(String dni) {
        return clienteRepositorio.findByDni(dni);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return clienteRepositorio.findAll();
    }
}
