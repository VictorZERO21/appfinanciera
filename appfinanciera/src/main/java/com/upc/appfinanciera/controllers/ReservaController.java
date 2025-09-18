package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.servicios.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    @PostMapping
    public ResponseEntity<ReservaDTO> insertarReserva(@RequestBody ReservaDTO reservaDTO) {
        ReservaDTO nuevaReserva = reservaService.insertar(reservaDTO);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }


    @GetMapping
    public List<ReservaDTO> buscarTodos() {
        return reservaService.buscarTodos();
    }


    @GetMapping("/{dniCliente}")
    public List<ReservaDTO> buscarPorCliente(@PathVariable String dniCliente) {
        return reservaService.buscarPorCliente(dniCliente);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
    }


    @PutMapping
    public ReservaDTO actualizar(@RequestBody ReservaDTO reservaDto) {
        return reservaService.actualizar(reservaDto);
    }
}
