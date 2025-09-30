package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.interfaces.IClienteService;
import com.upc.appfinanciera.servicios.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

   // @PostMapping
   // public ResponseEntity<ClienteDTO> insertar(@Valid @RequestBody ClienteDTO clienteDTO) {
   //     return ResponseEntity.ok(clienteService.insertarCliente(clienteDTO));
   // }

   // @PutMapping
   // public ResponseEntity<ClienteDTO> modificar(@Valid @RequestBody ClienteDTO clienteDTO) {
   //     return ResponseEntity.ok(clienteService.modificarCliente(clienteDTO));
   // }

   // @DeleteMapping("/{id}")
   // public void eliminar(@PathVariable Long id) {
   //     clienteService.eliminarCliente(id);
   // }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteDTO> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.buscarClientePorDni(dni));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.buscarClientePorEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClienteDTO>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(clienteService.buscarClientesPorNombre(nombre));
    }
}

