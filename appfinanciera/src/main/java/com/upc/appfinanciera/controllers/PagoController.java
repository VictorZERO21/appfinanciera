package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.PagoDTO;
import com.upc.appfinanciera.interfaces.IPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')") //necesito cliente
    public ResponseEntity<PagoDTO> insertar(@Valid @RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.ok(pagoService.insertarPago(pagoDTO));
    }


    @PutMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PagoDTO> modificar(@Valid @RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.ok(pagoService.modificarPago(pagoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public void eliminar(@PathVariable Long id) {
        pagoService.eliminarPago(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarPagoPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }
}
