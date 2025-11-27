package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.interfaces.IReservaService;
import com.upc.appfinanciera.servicios.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

   @Autowired
    private IReservaService reservaService;
    @PostMapping("/reservas")
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<ReservaDTO> insertar(@Valid @RequestBody ReservaDTO reservaDTO) {
        return new ResponseEntity<>(reservaService.insertarReserva(reservaDTO), HttpStatus.CREATED);
    }
    @PutMapping
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<ReservaDTO> modificar(@Valid @RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.ok(reservaService.modificarReserva(reservaDTO));
    }
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }
    @GetMapping
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<ReservaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }
    @GetMapping("/cliente/{idCliente}")
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<ReservaDTO>> listarPorClienteId(@PathVariable Long idCliente) {
        return ResponseEntity.ok(reservaService.listarReservasPorClienteId(idCliente));
    }
    @GetMapping("/asesor/{idAsesor}")
    //@PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<ReservaDTO>> listarPorAsesorId(@PathVariable Long idAsesor) {
        return ResponseEntity.ok(reservaService.listarReservasPorAsesorId(idAsesor));
    }
   
   @GetMapping("/asesor/{idAsesor}/clientes-reservados")
    public ResponseEntity<List<ClienteDTO>> listarClientesPorAsesor(@PathVariable Long idAsesor) {
        return ResponseEntity.ok(reservaService.listarClientesPorAsesor(idAsesor));
    }
}



