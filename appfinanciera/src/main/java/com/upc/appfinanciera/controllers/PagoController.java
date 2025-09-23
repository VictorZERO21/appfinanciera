package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.PagoDTO;
import com.upc.appfinanciera.interfaces.IPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoDTO> insertar(@Valid @RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.ok(pagoService.insertarPago(pagoDTO));
    }

    @PutMapping
    public ResponseEntity<PagoDTO> modificar(@Valid @RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.ok(pagoService.modificarPago(pagoDTO));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pagoService.eliminarPago(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarPagoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }
}
