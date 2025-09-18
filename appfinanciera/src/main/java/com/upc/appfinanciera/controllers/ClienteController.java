package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ClienteDTO;
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
    public ClienteDTO insertar(@RequestBody ClienteDTO clienteDto) {
        return clienteService.insertar(clienteDto);
    }


    @GetMapping
    public List<ClienteDTO> buscarTodos() {
        return clienteService.buscarTodos();
    }


    @GetMapping("/{dni}")
    public ClienteDTO buscarPorDni(@PathVariable String dni) {
        return clienteService.buscarPorDni(dni);
    }


    @DeleteMapping("/{dni}")
    public void eliminar(@PathVariable String dni) {
        clienteService.eliminar(dni);
    }


    @PutMapping
    public ClienteDTO actualizar(@RequestBody ClienteDTO clienteDto) {
        return clienteService.actualizar(clienteDto);
    }
}
