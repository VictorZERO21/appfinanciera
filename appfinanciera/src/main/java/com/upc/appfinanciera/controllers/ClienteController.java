package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.interfaces.IClienteService;
import com.upc.appfinanciera.servicios.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/by-username")
    public ResponseEntity<ClienteDTO> obtenerPorUsername(@RequestParam String emaill) {
        return ResponseEntity.ok(clienteService.findByEmail(emaill));
    }
}
