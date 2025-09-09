package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.servicios.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente insertar(@RequestBody Cliente cliente) {
        return clienteService.insertar(cliente);
    }

    @GetMapping
    public List<Cliente> buscarTodos() {
        return clienteService.buscarTodos();
    }

    @GetMapping("/{dni}")
    public Cliente buscarPorDni(@PathVariable String dni) {
        return clienteService.buscarPorDni(dni);
    }

    @DeleteMapping("/{dni}")
    public void eliminar(@PathVariable String dni) {
        clienteService.eliminar(dni);
    }

    @PutMapping
    public Cliente actualizar(@RequestBody Cliente cliente) {
        return clienteService.actualizar(cliente);
    }
}
