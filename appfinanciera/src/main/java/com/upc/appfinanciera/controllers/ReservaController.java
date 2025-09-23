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
    private IReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDTO> insertar(@Valid @RequestBody ReservaDTO reservaDTO) {
        return new ResponseEntity<>(reservaService.insertarReserva(reservaDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ReservaDTO> modificar(@Valid @RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.ok(reservaService.modificarReserva(reservaDTO));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ReservaDTO>> listarPorClienteId(@PathVariable Long idCliente) {
        return ResponseEntity.ok(reservaService.listarReservasPorClienteId(idCliente));
    }

    @GetMapping("/cliente/dni/{dniCliente}")
    public ResponseEntity<List<ReservaDTO>> listarPorClienteDni(@PathVariable String dniCliente) {
        return ResponseEntity.ok(reservaService.listarReservasPorClienteDni(dniCliente));
    }

    @GetMapping("/asesor/{idAsesor}")
    public ResponseEntity<List<ReservaDTO>> listarPorAsesorId(@PathVariable Long idAsesor) {
        return ResponseEntity.ok(reservaService.listarReservasPorAsesorId(idAsesor));
    }

    @GetMapping("/asesor/dni/{dniAsesor}")
    public ResponseEntity<List<ReservaDTO>> listarPorAsesorDni(@PathVariable String dniAsesor) {
        return ResponseEntity.ok(reservaService.listarReservasPorAsesorDni(dniAsesor));
    }
}

