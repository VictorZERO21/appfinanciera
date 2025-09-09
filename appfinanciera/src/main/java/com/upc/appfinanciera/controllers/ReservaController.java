package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.entidades.Reserva;
import com.upc.appfinanciera.servicios.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public Reserva insertar(@RequestBody Reserva reserva) {
        return reservaService.insertar(reserva);
    }

    @GetMapping
    public List<Reserva> buscarTodos() {
        return reservaService.buscarTodos();
    }

    @GetMapping("/{dniCliente}")
    public List<Reserva> buscarPorCliente(@PathVariable String dniCliente) {
        return reservaService.buscarPorCliente(dniCliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
    }

    @PutMapping
    public Reserva actualizar(@RequestBody Reserva reserva) {
        return reservaService.actualizar(reserva);
    }
}